package com.example.demo.mongo.daoImpl;

import com.example.demo.mongo.dao.EmployeeDao;
import com.example.demo.mongo.mongoEntity.EmployeesMO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeDaoImpl implements EmployeeDao {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Override
    public int save(EmployeesMO employeesMO) {
        mongoTemplate.save(employeesMO);
        return employeesMO.getEmpNo();
    }

    @Override
    public int update(EmployeesMO employeesMO) {
        return 0;
    }

    @Override
    public EmployeesMO getOneById(int empNo) {
        return mongoTemplate.findOne(Query.query(Criteria.where("empNo").is(empNo)), EmployeesMO.class);
    }

    @Override
    public void remove(int empNo) {
    }

    @Override
    public List<EmployeesMO> findAll() {
        return this.mongoTemplate.find(new Query(), EmployeesMO.class);
    }

    @Override
    public void insertAll(List<EmployeesMO> objs) {
        mongoTemplate.insertAll(objs);
    }
}
