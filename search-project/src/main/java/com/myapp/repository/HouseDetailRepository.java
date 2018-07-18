package com.myapp.repository;

import java.util.List;

import com.myapp.entity.HouseDetail;
import org.springframework.data.repository.CrudRepository;


/**
 * Created by cgh.
 */
public interface HouseDetailRepository extends CrudRepository<HouseDetail, Long>{
    HouseDetail findByHouseId(Long houseId);

    List<HouseDetail> findAllByHouseIdIn(List<Long> houseIds);
}
