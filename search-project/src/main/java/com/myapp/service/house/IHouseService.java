package com.myapp.service.house;

import com.myapp.service.ServiceMultiResult;
import com.myapp.service.ServiceResult;
import com.myapp.web.dto.HouseDTO;
import com.myapp.web.form.DatatableSearch;
import com.myapp.web.form.HouseForm;
import com.myapp.web.form.MapSearch;
import com.myapp.web.form.RentSearch;

/**
 * @author cgh
 */
public interface IHouseService {

    ServiceResult<HouseDTO> save(HouseForm houseForm);

    /**
     *   编辑
     * @param
     * @return
    */
    ServiceResult update(HouseForm houseForm);

    ServiceMultiResult<HouseDTO> adminQuery(DatatableSearch search);

    /**
     *  查询完整房源信息
     * @param
     * @return
    */
    ServiceResult<HouseDTO> findCompleteOne(Long id);

    /**
     * 移除图片
     * @param id
     * @return
     */
    ServiceResult removePhoto(Long id);

    /**
     * 更新封面
     * @param coverId
     * @param targetId
     * @return
     */
    ServiceResult updateCover(Long coverId, Long targetId);

    /**
     * 新增标签
     * @param houseId
     * @param tag
     * @return
     */
    ServiceResult addTag(Long houseId, String tag);

    /**
     * 移除标签
     * @param houseId
     * @param tag
     * @return
     */
    ServiceResult removeTag(Long houseId, String tag);

    /**
     * 更新房源状态
     * @param id
     * @param status
     * @return
     */
    ServiceResult updateStatus(Long id, int status);

    /**
     * 查询房源信息集
     * @param rentSearch
     * @return
     */
    ServiceMultiResult<HouseDTO> query(RentSearch rentSearch);

    /**
     *  全地图查询
     * @param
     * @return
    */
    ServiceMultiResult<HouseDTO> wholeMapQuery(MapSearch mapSearch);

    /**
     *   精确范围查询
     * @param
     * @return
    */
    ServiceMultiResult<HouseDTO> boundMapQuery(MapSearch mapSearch);
}
