package com.example.demo.service.impl;

import com.example.demo.utils.KafkaConfig;
import com.example.demo.utils.RedisKeysConstant;
import com.example.demo.dao.StockOrderMapper;
import com.example.demo.domain.Stock;
import com.example.demo.domain.StockOrder;
import com.example.demo.service.OrderService;
import com.example.demo.service.StockService;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author cgh
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class OrderServiceImpl implements OrderService {

    private Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private StockService stockService;

    @Autowired
    private StockOrderMapper orderMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private KafkaProducer kafkaProducer;

    @Value("${kafka.topic}")
    private String kafkaTopic;

    @Override
    public int createWrongOrder(int sid) throws Exception {
        //校验库存
        Stock stock = checkStock(sid);
        //扣库存
        saleStock(stock);
        //创建订单
        return createOrder(stock);
    }
    private Stock checkStock(int sid){
        Stock stock = stockService.getStockById(sid);
        if(stock.getSale().equals(stock.getCount())){
            throw new RuntimeException("库存不足");
        }
        return stock;
    }
    private void saleStock(Stock stock){
       // stock.setSale(stock.getSale() + 1);
      //  System.out.println(stock.getSale());

      //  return stockService.updateStockById(stock);
        //使用乐观锁更新库存
        int count = stockService.updateStockByOptimistic(stock);
        if(count == 0){
            throw new RuntimeException("并发更新库存失败");
        }
    }
    private int createOrder(Stock stock){
        StockOrder stockOrder = new StockOrder();
        stockOrder.setSid(stock.getId());
        stockOrder.setName(stock.getName());
        int id = orderMapper.insertSelective(stockOrder);
        return id;
    }

    /**
     *创建订单 乐观锁 ， 库存查redis减小DB压力
     * @param
     * @return
    */
    @Override
    public int createOptimisticOrderUseRedis(int sid) throws Exception {

        //校验库存， 从redis中获取
        Stock stock = checkStockByRedis(sid);
        //乐观锁更新库存，以及更新redis
        saleStockOptimisticByRedis(stock);
        //创建订单
        return createOrder(stock);
    }

    private Stock checkStockByRedis(int sid) throws Exception{
        Integer count = Integer.parseInt(redisTemplate.opsForValue().get(RedisKeysConstant.STOCK_COUNT + sid));
        //System.out.println("count == " + count);
        Integer sale = Integer.parseInt(redisTemplate.opsForValue().get(RedisKeysConstant.STOCK_SALE + sid));
        if(count.equals(sale)){
            throw new RuntimeException("库存不足 Redis currentCount = " + sale);
        }
        Integer version = Integer.parseInt(redisTemplate.opsForValue().get(RedisKeysConstant.STOCK_VERSION + sid));
        Stock stock = new Stock();
        stock.setId(sid);
        stock.setCount(count);
        stock.setSale(sale);
        stock.setName("手机");
        stock.setVersion(version);
        return stock;
    }
    private void saleStockOptimisticByRedis(Stock stock){
        int count = stockService.updateStockByOptimistic(stock);
        if(count == 0){
            throw new RuntimeException("并发更新库存失败");
        }
        //自增 更新redis
        redisTemplate.opsForValue().increment(RedisKeysConstant.STOCK_SALE + stock.getId(), 1);
        redisTemplate.opsForValue().increment(RedisKeysConstant.STOCK_VERSION + stock.getId(), 1);
    }

    /**
     * 创建订单 乐观锁，库存查 Redis 减小 DB 压力。
     * 利用 Kafka 异步写订单
     * @param sid
     * @return
     * @throws Exception
     */
    @Override
    public void createOptimisticOrderUseRedisAndKafka(int sid) throws Exception {

        //检验库存，从 Redis 获取
        Stock stock = checkStockByRedis(sid);

        //乐观锁更新库存
        saleStockOptimisticByRedis(stock);

        //利用 Kafka 创建订单
        kafkaProducer.send(new ProducerRecord(kafkaTopic,stock)) ;
        logger.info("send Kafka success");
}

    /**
     *
     kafka异步创建订单
     */
    @Override
    public void createOrderByKafka(Stock stock) throws Exception {
        //创建订单
        createOrder(stock);
    }
}
