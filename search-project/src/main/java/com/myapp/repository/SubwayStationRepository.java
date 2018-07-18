package com.myapp.repository;

import java.util.List;

import com.myapp.entity.SubwayStation;
import org.springframework.data.repository.CrudRepository;


/**
 * Created by cgh.
 */
public interface SubwayStationRepository extends CrudRepository<SubwayStation, Long> {
    List<SubwayStation> findAllBySubwayId(Long subwayId);
}
