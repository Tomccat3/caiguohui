package com.example.demo;

import com.example.demo.mongo.dao.EmployeeDao;
import com.example.demo.mongo.mongoEntity.EmployeesMO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class MongoTest {
    @Autowired
    EmployeeDao employeeDao;

    @Test
    public void Test(){

        List<EmployeesMO> objs = new ArrayList<EmployeesMO>();
        for(int i = 0; i < 1000000; i++){
            objs.add(new EmployeesMO(i,"tom" + i % 1000,i % 2 == 0 ? 1 : 0));
        }
        employeeDao.insertAll(objs);

       /* EmployeesMO employeesMO = new EmployeesMO();
        employeesMO.setEmpNo(1);
        employeesMO.setGender(1);
        employeeDao.save(employeesMO);*/

        /*List<EmployeesMO> list = new ArrayList<EmployeesMO>();
        list = employeeDao.findAll();
        for(int i = 0;i < list.size(); i++){
            System.out.println(list.get(i).toString());
        }*/

    }

}
