package com.example.demo.service;

import com.example.demo.domain.Stock;
import org.springframework.stereotype.Service;

/**
 * @author cgh
 */
public interface OrderService {

    /**
     *创建订单
     * @param
     * @return
    */
    int createWrongOrder(int sid) throws Exception;

    /**
     *创建订单 乐观锁 ， 库存查redis减小DB压力
     * @param
     * @return
    */
    int createOptimisticOrderUseRedis(int sid) throws Exception;

    /**
     * 创建订单 乐观锁，库存查 Redis 减小 DB 压力。
     * 利用 Kafka 异步写订单
     * @param sid
     * @return
     * @throws Exception
     */
    void createOptimisticOrderUseRedisAndKafka(int sid) throws Exception;

    //创建订单
    void createOrderByKafka(Stock stock) throws Exception;
}
