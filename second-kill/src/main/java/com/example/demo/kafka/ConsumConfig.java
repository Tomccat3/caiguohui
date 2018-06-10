package com.example.demo.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author cgh
 */
@Configuration
public class ConsumConfig {

    private final static Logger LOGGER = LoggerFactory.getLogger(ConsumConfig.class);

    @Value("${consumer.broker.list}")
    private String brokerList;

    @Value("${consumer.group.id}")
    private String groupId;

    @Value("${consumer.topic}")
    private String topic;

    @Value("${consumer.thread}")
    private int threadNum;

    @Bean(value = "consumerGroup")
    public ConsumerGroup createConsumerGroup() {
        ConsumerGroup consumerGroup = new ConsumerGroup(threadNum, groupId, topic, brokerList);
        LOGGER.info("ConsumerGroup 初始化成功");
        return consumerGroup;
    }
}
