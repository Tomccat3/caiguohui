package com.example.demo.repositories;

import com.example.demo.entity.EmployeesDO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface EmployeesRepository extends ElasticsearchRepository<EmployeesDO,Integer> {

}
