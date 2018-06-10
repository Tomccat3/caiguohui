package com.example.demo.kafka;

import com.example.demo.domain.Stock;
import com.example.demo.service.OrderService;
import com.example.demo.utils.DecodeingKafka;
import com.example.demo.utils.SpringBeanFactory;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.RejectedExecutionException;

/**
 * @author cgh
 */
public class ConsumerTask implements Runnable{

    private static Logger LOGGER = LoggerFactory.getLogger(ConsumerTask.class);

    /*
      每个线程维护一个consumer实例
     */
    private final KafkaConsumer<String, Stock> consumer;

    private OrderService orderService;

    public ConsumerTask(String brokerList, String groupId, String topic){

        this.orderService = SpringBeanFactory.getBean(OrderService.class);

        Properties props = new Properties();
        props.put("bootstrap.servers", brokerList);
        props.put("group.id", groupId);
        //自动提交位移
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "com.example.demo.utils.DecodeingKafka");
        this.consumer = new KafkaConsumer<String, Stock>(props);
        consumer.subscribe(Arrays.asList(topic));
    }
    @Override
    public void run() {
        boolean flag = true;
        Stock stock;
        while (flag){
            //获取超时时间设置为200ms
            ConsumerRecords<String, Stock> records = consumer.poll(200);
            for (ConsumerRecord<String, Stock> record : records){
                //打印消息
                LOGGER.info("==="+record.value().toString() + " consumed " + record.partition() +
                        " message with offset: " + record.offset());
                stock = record.value();
                dealMessage(stock);
            }
        }
    }

    private void dealMessage(Stock stock){
        try{
            orderService.createOrderByKafka(stock);
        }catch (RejectedExecutionException e){
            LOGGER.error("rejected message = " + stock.toString());
        }catch (Exception e){
            LOGGER.error("unknown exception, " ,e);
        }
    }
}
