package com.example.demo.controller;

import com.example.demo.entity.Email;
import com.example.demo.kafka.KafkaProducers;
import com.example.demo.util.ChConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaController {

    @Autowired
    private KafkaProducers kafkaProducers;

    @PostMapping("/mail/send")
    public String sendMessage(@RequestBody Email email) {
        kafkaProducers.sendMessage(email);
        return email.getEmail();
    }
}
