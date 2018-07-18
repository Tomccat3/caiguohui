package com.myapp.web.controller.house;

import com.myapp.base.ApiResponse;
import com.myapp.base.RentValueBlock;
import com.myapp.entity.SupportAddress;
import com.myapp.service.IUserService;
import com.myapp.service.ServiceMultiResult;
import com.myapp.service.ServiceResult;
import com.myapp.service.house.IAddressService;
import com.myapp.service.house.IHouseService;
import com.myapp.service.search.HouseBucketDTO;
import com.myapp.service.search.ISearchService;
import com.myapp.web.dto.*;
import com.myapp.web.form.MapSearch;
import com.myapp.web.form.RentSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author cgh
 */
@Controller
public class HouseController {
    @Autowired
    private IAddressService addressService;

    @Autowired
    private IHouseService houseService;

    @Autowired
    private IUserService userService;

    @Autowired
    private ISearchService searchService;

    /**
     *  自动补全接口
     * @param
     * @return
    */
    @GetMapping("rent/house/autocomplete")
    @ResponseBody
    public ApiResponse autocomplete(@RequestParam(value = "prefix") String prefix){
        if (prefix.isEmpty()){
            return ApiResponse.ofStatus(ApiResponse.Status.BAD_REQUEST);
        }
        ServiceResult<List<String>> res = this.searchService.suggest(prefix);
        return ApiResponse.ofSuccess(res.getResult());
    }

    @GetMapping("address/support/cities")
    @ResponseBody
    public ApiResponse getSupportCities(){
        ServiceMultiResult<SupportAddressDTO> result = addressService.findAllCities();
        if (result.getResultSize() == 0){
            return ApiResponse.ofSuccess(ApiResponse.Status.NOT_FOUND);
        }
        return ApiResponse.ofSuccess(result.getResult());
    }

    /**
     * 获取对应城市支持区域列表
     * @param cityEnName
     * @return
     */
    @GetMapping("address/support/regions")
    @ResponseBody
    public ApiResponse getSupportRegions(@RequestParam(name = "city_name") String cityEnName) {
        ServiceMultiResult<SupportAddressDTO> addressResult = addressService.findAllRegionsByCityName(cityEnName);
        if (addressResult.getResult() == null || addressResult.getTotal() < 1) {
            return ApiResponse.ofStatus(ApiResponse.Status.NOT_FOUND);
        }
        return ApiResponse.ofSuccess(addressResult.getResult());
    }

    /**
     * 获取具体城市所支持的地铁线路
     * @param cityEnName
     * @return
     */
    @GetMapping("address/support/subway/line")
    @ResponseBody
    public ApiResponse getSupportSubwayLine(@RequestParam(name = "city_name") String cityEnName) {
        List<SubwayDTO> subways = addressService.findAllSubwayByCity(cityEnName);
        if (subways.isEmpty()) {
            return ApiResponse.ofStatus(ApiResponse.Status.NOT_FOUND);
        }

        return ApiResponse.ofSuccess(subways);
    }

    /**
     * 获取对应地铁线路所支持的地铁站点
     * @param subwayId
     * @return
     */
    @GetMapping("address/support/subway/station")
    @ResponseBody
    public ApiResponse getSupportSubwayStation(@RequestParam(name = "subway_id") Long subwayId) {
        List<SubwayStationDTO> stationDTOS = addressService.findAllStationBySubway(subwayId);
        if (stationDTOS.isEmpty()) {
            return ApiResponse.ofStatus(ApiResponse.Status.NOT_FOUND);
        }

        return ApiResponse.ofSuccess(stationDTOS);
    }

    @GetMapping("rent/house")
    public String renHOusePage(@ModelAttribute RentSearch rentSearch, Model model, HttpSession session,
                               RedirectAttributes redirectAttributes){
        if (rentSearch.getCityEnName() == null){
            String cityEnNameSession = (String) session.getAttribute("cityEnName");
            if (cityEnNameSession == null){
                redirectAttributes.addAttribute("msg", "must_chose_city");
                return "redirect:/index";
            }else {
                rentSearch.setCityEnName(cityEnNameSession);
            }
        }else {
            session.setAttribute("cityEnName", rentSearch.getCityEnName());
        }

        ServiceResult<SupportAddressDTO> city = addressService.findCity(rentSearch.getCityEnName());
        if (!city.isSuccess()) {
            redirectAttributes.addAttribute("msg", "must_chose_city");
            return "redirect:/index";
        }
        model.addAttribute("currentCity", city.getResult());

        ServiceMultiResult<SupportAddressDTO> addressResult = addressService.findAllRegionsByCityName(rentSearch.getCityEnName());
        if (addressResult.getResult() == null || addressResult.getTotal() < 1){
            redirectAttributes.addAttribute("msg", "must_chose_city");
            return "redirect:/index";
        }

        ServiceMultiResult<HouseDTO> serviceMultiResult = houseService.query(rentSearch);

        model.addAttribute("total", serviceMultiResult.getTotal());
        model.addAttribute("houses", serviceMultiResult.getResult());
        if (rentSearch.getRegionEnName() == null) {
            rentSearch.setRegionEnName("*");
        }
        model.addAttribute("searchBody", rentSearch);
        model.addAttribute("regions", addressResult.getResult());
        model.addAttribute("priceBlocks", RentValueBlock.PRICE_BLOCK);
        model.addAttribute("areaBlocks", RentValueBlock.AREA_BLOCK);

        model.addAttribute("currentPriceBlock", RentValueBlock.matchPrice(rentSearch.getPriceBlock()));
        model.addAttribute("currentAreaBlock", RentValueBlock.matchArea(rentSearch.getAreaBlock()));

        return "rent-list";

    }

    @GetMapping("rent/house/show/{id}")
    public String show(@PathVariable(value = "id") Long houseId, Model model){
        if (houseId <= 0){
            return "404";
        }

        ServiceResult<HouseDTO> serviceResult = houseService.findCompleteOne(houseId);
        if (!serviceResult.isSuccess()){
            return "404";
        }

        HouseDTO houseDTO = serviceResult.getResult();
        Map<SupportAddress.Level, SupportAddressDTO>
                addressMap = addressService.findCityAndRegion(houseDTO.getCityEnName(), houseDTO.getRegionEnName());

        SupportAddressDTO city = addressMap.get(SupportAddress.Level.CITY);
        SupportAddressDTO region = addressMap.get(SupportAddress.Level.REGION);

        model.addAttribute("city", city);
        model.addAttribute("region", region);

        ServiceResult<UserDTO> userDTOServiceResult = userService.findById(houseDTO.getAdminId());
        model.addAttribute("agent", userDTOServiceResult.getResult());
        model.addAttribute("house", houseDTO);

        ServiceResult<Long> aggResult = searchService.aggregateDistrictHouse(city.getEnName(), region.getEnName(), houseDTO.getDistrict());
        model.addAttribute("houseCountInDistrict", aggResult.getResult());
        return "house-detail";
    }

    @GetMapping("rent/house/map")
    public String rentMapPage(@RequestParam(value = "cityEnName") String cityEnName, Model model, HttpSession session,
                              RedirectAttributes redirectAttributes){
        ServiceResult<SupportAddressDTO> city = addressService.findCity(cityEnName);
        if (!city.isSuccess()){
            redirectAttributes.addAttribute("msg", "must_chose_city");
            return "redirect:/index";
        }else {
            session.setAttribute("cityName", cityEnName);
            model.addAttribute("city", city.getResult());
        }
        ServiceMultiResult<SupportAddressDTO> regions = addressService.findAllRegionsByCityName(cityEnName);

        ServiceMultiResult<HouseBucketDTO> serviceMultiResult = searchService.mapAggregate(cityEnName);
        model.addAttribute("aggData", serviceMultiResult.getResult());
        model.addAttribute("total", serviceMultiResult.getTotal());
        model.addAttribute("regions", regions.getResult());
        return "rent-map";
    }

    @GetMapping("rent/house/map/houses")
    @ResponseBody
    public ApiResponse rentMapHouses(@ModelAttribute MapSearch mapSearch){
        if (mapSearch.getCityEnName() == null){
            return ApiResponse.ofMessage(HttpStatus.BAD_REQUEST.value(), "必须选择城市");
        }
        ServiceMultiResult<HouseDTO> serviceResult;
        if (mapSearch.getLevel() < 13){
            serviceResult = houseService.wholeMapQuery(mapSearch);
        }else {
            //传递地图边界
            serviceResult = houseService.boundMapQuery(mapSearch);
        }
        ApiResponse response = ApiResponse.ofSuccess(serviceResult.getResult());
        response.setMore(serviceResult.getTotal() > (mapSearch.getStart() + mapSearch.getSize()));
        return response;
    }
}
