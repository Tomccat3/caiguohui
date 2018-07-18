package com.myapp.repository;

import java.util.List;

import com.myapp.entity.HouseTag;
import org.springframework.data.repository.CrudRepository;


/**
 * Created by cgh.
 */
public interface HouseTagRepository extends CrudRepository<HouseTag, Long> {
    HouseTag findByNameAndHouseId(String name, Long houseId);

    List<HouseTag> findAllByHouseId(Long id);

    List<HouseTag> findAllByHouseIdIn(List<Long> houseIds);
}
