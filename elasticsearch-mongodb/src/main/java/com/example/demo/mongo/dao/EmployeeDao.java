package com.example.demo.mongo.dao;

import com.example.demo.mongo.mongoEntity.EmployeesMO;

import java.util.List;

public interface EmployeeDao {

    int save(EmployeesMO employeesMO);

    int update(EmployeesMO employeesMO);

    EmployeesMO getOneById(int empNo);

    void remove(int empNo);

    List<EmployeesMO> findAll();

    void insertAll(List<EmployeesMO> objs);
}
