package com.example.demo.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.util.Date;

@Document(indexName = "company",type = "employe" , shards = 5, replicas = 1)
public class EmployeesDO implements Serializable {
    private static final long serialVersionUID = -5486342676464419079L;

    public EmployeesDO(Integer empNo, String firstName, int gender) {
        this.empNo = empNo;
        this.firstName = firstName;
        this.gender = gender;
    }

    public EmployeesDO() {
    }

    @Id
    private Integer empNo;

    private String firstName;

    private int gender;

    public Integer getEmpNo() {
        return empNo;
    }

    public void setEmpNo(Integer empNo) {
        this.empNo = empNo;
    }

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

    @Override
    public String toString() {
        return "EmployeesDO{" +
                "empNo=" + empNo +
                ", firstName='" + firstName + '\'' +
                ", gender=" + gender +
                '}';
    }
}