package com.example.demo.mongo.mongoEntity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "employee")
public class EmployeesMO {

    public EmployeesMO(Integer empNo, String firstName, int gender) {
        this.empNo = empNo;
        this.firstName = firstName;
        this.gender = gender;
    }

    public EmployeesMO(){}
    @Id
    private String id;

    private Integer empNo;

    private String firstName;

    private int gender;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getEmpNo() {
        return empNo;
    }

    public void setEmpNo(Integer empNo) {
        this.empNo = empNo;
    }

    @Override
    public String toString() {
        return "EmployeesMO{" +
                "id='" + id + '\'' +
                ", empNo=" + empNo +
                ", firstName='" + firstName + '\'' +
                '}';
    }
}
