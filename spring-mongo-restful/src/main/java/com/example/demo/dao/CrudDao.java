package com.example.demo.dao;

import com.example.demo.entity.Notice;

import java.util.List;

public interface CrudDao {

    String save(Notice notice);

    void update(Notice notice);

    Notice getOneById(int siteId);

    void remove(int siteId);

    List<Notice> findAll();
}
