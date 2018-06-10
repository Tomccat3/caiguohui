package com.example.demo;

import com.example.demo.entity.EmployeesDO;
import com.example.demo.mongo.dao.EmployeeDao;
import com.example.demo.mongo.mongoEntity.EmployeesMO;
import com.example.demo.repositories.EmployeesRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class mongoElasticTest{

    @Autowired
    EmployeeDao employeeDao;

    @Autowired
    EmployeesRepository employeesRepository;

    //mongo-elasticsearch
    @Test
    public void test1(){
        List<EmployeesMO> list = new ArrayList<>();
        list = employeeDao.findAll();
        System.out.print(list.size());
        Iterable<EmployeesDO> insert = new ArrayList<>();
        EmployeesMO tmp = new EmployeesMO();
        for(int i = 0; i < list.size(); i++){
            tmp = list.get(i);
            ((ArrayList<EmployeesDO>) insert).add(new EmployeesDO(tmp.getEmpNo(),tmp.getFirstName(),tmp.getGender()));
        }
        employeesRepository.save(insert);
    }

    //elasticsearch-mongo
    @Test
    public void test2(){
        Iterable<EmployeesDO> list = new ArrayList<>();
        list = employeesRepository.findAll();
        List<EmployeesMO> res = new ArrayList<>();
        for (EmployeesDO tmp: list) {
            res.add(new EmployeesMO(tmp.getEmpNo(), tmp.getFirstName(), tmp.getGender()));
        }
        employeeDao.insertAll(res);
    }
}
