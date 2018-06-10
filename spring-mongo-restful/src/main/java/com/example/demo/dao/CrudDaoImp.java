package com.example.demo.dao;

import com.example.demo.entity.Notice;
import com.mongodb.WriteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CrudDaoImp implements CrudDao {

    @Autowired
    protected MongoOperations mongoOperations;

    @Autowired
    private MongoTemplate mongoTemplate;

    //插入记录
    public String save(Notice notice){
        mongoTemplate.save(notice);
        return notice.getId();
    }

    //更新记录
    public void update(Notice notice){
        Query query = new Query();
        query.addCriteria(Criteria.where("siteId").is(notice.getSiteId()));
        Update update = new Update();
        update.set("title", notice.getTitle());
        update.set("creator", notice.getCreator());
        update.set("content", notice.getContent());
        update.set("updateTime", new Date());
        mongoTemplate.updateFirst(query, update, Notice.class);

    }

    //根据id查找一条记录
    public Notice getOneById(int siteId){
        /*Query query = new Query();
        Criteria criteria = Criteria.where("siteId").is(siteId);
        query.addCriteria(criteria);
        Notice notice = this.mongoTemplate.findOne(query, Notice.class);*/

        Notice notice = mongoOperations.findOne(
                Query.query(Criteria.where("siteId").is(siteId)), Notice.class);
        return notice;
    }

    //根据id删除一条记录
    public void remove(int siteId){
        this.mongoTemplate.remove(
                Query.query(Criteria.where("siteId").is(siteId)), Notice.class);
    }

    //获取所有记录
    public List<Notice> findAll() {
        return this.mongoTemplate.find(new Query(), Notice.class);
    }
}
