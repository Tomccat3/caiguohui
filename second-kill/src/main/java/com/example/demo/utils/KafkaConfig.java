package com.example.demo.utils;

import com.example.demo.domain.Stock;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author cgh
 */
@Configuration
public class KafkaConfig {

    @Value("${kafka.brokerList}")
    private String brokerList ;

    @Bean
    public KafkaProducer<String, Stock> build(){
        //初始化生产者
        Map<String, Object> props = new HashMap<String, Object>(16);
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        props.put("metadata.broker.list", brokerList);
        props.put("bootstrap.servers", brokerList);
        props.put("key.serializer", StringSerializer.class);
        props.put("value.serializer", EncodeingKafka.class);
        KafkaProducer<String, Stock> producer = new KafkaProducer(props);
        return producer ;
    }
}
