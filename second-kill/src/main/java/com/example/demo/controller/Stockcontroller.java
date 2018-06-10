package com.example.demo.controller;

import com.example.demo.dao.StockMapper;
import com.example.demo.domain.Stock;
import com.example.demo.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cgh
 */
@RestController
public class Stockcontroller {

    private Logger logger = LoggerFactory.getLogger(Stockcontroller.class);

    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private OrderService orderService;

    @RequestMapping("findOne/{id}")
    @ResponseBody
    public Stock findOne(@PathVariable("id") Integer id){
        return stockMapper.selectById(id);
    }

    @RequestMapping("secondKill/{sid}")
    @ResponseBody
    public String createWrongOrder(@PathVariable Integer sid){
        logger.info("sid=[{}]", sid);
        int id = 0;
        try{
            id = orderService.createWrongOrder(sid);
        }catch (Exception e){
            logger.error("Exception", e);
        }
        return String.valueOf(id);
    }

    //乐观锁 + redis
    @RequestMapping("secondKill1/{sid}")
    @ResponseBody
    public String createOptimisticOrderUseRedis(@PathVariable Integer sid){
        logger.info("sid=[{}]", sid);
        int id = 0;
        try{
            id = orderService.createOptimisticOrderUseRedis(sid);
        }catch (Exception e){
            logger.error("Exception", e);
        }
        return String.valueOf(id);
    }

    /**
     * 乐观锁 + redis + kafka异步创建订单
     */
    @RequestMapping("secondKill2/{sid}")
    @ResponseBody
    public String createOptimisticOrderUseRedisAndKafka(@PathVariable Integer sid){
        logger.info("sid=[{}]", sid);
        int id = 0;
        try{
            orderService.createOptimisticOrderUseRedisAndKafka(sid);
        }catch (Exception e){
            logger.error("Exception", e);
        }
        return String.valueOf(id);
    }
}
