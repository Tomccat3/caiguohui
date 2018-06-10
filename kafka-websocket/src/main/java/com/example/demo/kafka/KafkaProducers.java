package com.example.demo.kafka;

import com.example.demo.entity.Email;
import com.example.demo.util.ChConstants;
import com.example.demo.util.EncodeingKafka;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class KafkaProducers {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducers.class);

    @Autowired
    private KafkaTemplate kafkaTemplate;

    public void sendMessage(Email mail){
        Properties props = new Properties();
        props.setProperty("bootstrap.servers", "localhost:9091,localhost:9092,localhost:9093");
        props.put("key.serializer", StringSerializer.class);
        props.put("value.serializer", EncodeingKafka.class);
        KafkaProducer kafkaProducer = new KafkaProducer(props);
        ProducerRecord<String, Email> producerRecord = new ProducerRecord<String, Email>(ChConstants.KAFKA_LISTENER_TOPIC.getTopic(),
                0, mail.getSubject(), mail);

        kafkaProducer.send(producerRecord, new Callback() {
            @Override
            public void onCompletion(RecordMetadata recordMetadata, Exception exception) {
                if (recordMetadata != null) {
                    System.out.println("  发送成功："+"checksum: "+recordMetadata.checksum()+" offset: "+recordMetadata.offset()+" partition: "+recordMetadata.partition()+" topic: "+recordMetadata.topic());
                }
                if (exception != null) {
                    System.out.println("异常："+exception.getMessage());
                }
            }
        });
        kafkaProducer.close();

//        Future<RecordMetadata> future = kafkaProducer.send(producerRecord);
//
//        RecordMetadata recordMetadata = future.get();
//
//        logger.info("recordMetadata: {}", recordMetadata.toString());
    }
}
