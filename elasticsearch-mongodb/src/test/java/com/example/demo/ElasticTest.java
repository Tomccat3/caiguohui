package com.example.demo;
import com.example.demo.repositories.EmployeesRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ElasticTest {

    @Autowired
    EmployeesRepository employeesRepository;
    @Test
    public void Test() {
        employeesRepository.deleteAll();
    }

}  