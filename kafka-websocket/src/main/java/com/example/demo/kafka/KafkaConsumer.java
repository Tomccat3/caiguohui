package com.example.demo.kafka;

import com.example.demo.entity.Email;
import com.example.demo.entity.Response;
import com.example.demo.mailService.IMailService;
import com.example.demo.util.ChConstants;
import com.example.demo.webSocket.service.WebsocketService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class KafkaConsumer {

    private Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    @Autowired
    private IMailService mailService;

    @Autowired
    private WebsocketService websocketService;

    @KafkaListener(topics = ChConstants.Constants.KAFKA_LISTENER_TOPIC)
    public void listen(ConsumerRecord<String, Email> record) throws Exception{

        Optional<Email> kafkaMessage = Optional.ofNullable(record.value());
        if(kafkaMessage.isPresent()){
            Email message = kafkaMessage.get();
            boolean res = mailService.send(message);
            logger.info("record = " + record);
            logger.info("message = " + message);
            Response response = websocketService.send(message);
            logger.info("send websocket response : {}", response.toString());
        }
    }
}
