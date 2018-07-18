package com.myapp.service.search;

import com.myapp.service.ServiceMultiResult;
import com.myapp.service.ServiceResult;
import com.myapp.web.form.MapSearch;
import com.myapp.web.form.RentSearch;

import java.util.List;

/**
 *   检索接口
 * @author cgh
 */
public interface ISearchService {

    /**
     *  索引目标房源
     * @param
     * @return
    */
    void index(Long houseId);

    /**
     * 移除房源索引
     * @param
     * @return
    */
    void remove (Long houseId);

    /**
     *  查询房源接口
     *  @param
     * @return
    */
    ServiceMultiResult<Long> query(RentSearch rentSearch);

    /**
     *  获取补全建议关键词
     * @param
     * @return
    */
    ServiceResult<List<String>> suggest(String prefix);

    /**
     *  聚合某个区域的房数
     * @param
     * @return
    */
    ServiceResult<Long> aggregateDistrictHouse(String cityEnName, String regionEnName, String district);

    /**
     *  聚合城市数据
     * @param
     * @return
    */
    ServiceMultiResult<HouseBucketDTO> mapAggregate(String cityEnName);

    /**
     *
     * @param
     * @return
    */
    ServiceMultiResult<Long> mapQuery(String cityEnName, String orderBy, String orderDirection, int start, int size);

    /**
     *   精确查询
     * @param
     * @return
    */
    ServiceMultiResult<Long> mapQuery(MapSearch search);
}
