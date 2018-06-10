package com.example.demo.controller;

import com.example.demo.dao.CrudDao;
import com.example.demo.entity.Notice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController()
public class MongoController {
    @Autowired
    private CrudDao crudDao;

    @PostMapping(value = "/save")
    public  @ResponseBody String saveRecord(@RequestBody Notice notice){
        Date date = new Date();
        notice.setCreatTime(date);
        notice.setUpdateTime(date);
        return crudDao.save(notice);
    }

    @GetMapping(value = "/getOne/{siteId}")
    public @ResponseBody Notice getOneById(@PathVariable("siteId") Integer siteId) {
        Notice notice = crudDao.getOneById(siteId);
        System.out.print(notice.toString());
        return notice;
    }

    @RequestMapping(value = "/remove/{siteId}",method = RequestMethod.DELETE)
    public @ResponseBody void remove(@PathVariable("siteId") Integer siteId){
        crudDao.remove(siteId);
    }

    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    @ResponseBody
    public void update(@RequestBody Notice notice){
        crudDao.update(notice);
    }

    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    public @ResponseBody
    List<Notice> listAll(){
        List<Notice> list = new ArrayList<Notice>();
        list = this.crudDao.findAll();
        return list;
    }
}
