package com.myapp.repository;

import java.util.List;

import com.myapp.entity.HousePicture;
import org.springframework.data.repository.CrudRepository;


/**
 * Created by cgh.
 */
public interface HousePictureRepository extends CrudRepository<HousePicture, Long> {
    List<HousePicture> findAllByHouseId(Long id);
}
