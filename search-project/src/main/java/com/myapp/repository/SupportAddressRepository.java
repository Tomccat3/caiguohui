package com.myapp.repository;

import com.myapp.entity.SupportAddress;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author cgh
 */
public interface SupportAddressRepository extends CrudRepository<SupportAddress, Long> {

    /**
     *  获取所有对应行政级别的信息
     * @param
     * @return
    */
    List<SupportAddress> findAllByLevel(String level);

    SupportAddress findByEnNameAndLevel(String enName, String level);

    SupportAddress findByEnNameAndBelongTo(String enName, String belongTo);

    List<SupportAddress> findAllByLevelAndBelongTo(String level, String belongTo);
}
