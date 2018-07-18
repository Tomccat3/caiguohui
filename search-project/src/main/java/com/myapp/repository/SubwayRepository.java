package com.myapp.repository;

import java.util.List;

import com.myapp.entity.Subway;
import org.springframework.data.repository.CrudRepository;


/**
 * Created by cgh.
 */
public interface SubwayRepository extends CrudRepository<Subway, Long>{
    List<Subway> findAllByCityEnName(String cityEnName);
}
