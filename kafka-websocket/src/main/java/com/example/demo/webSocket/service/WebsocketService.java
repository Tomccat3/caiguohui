package com.example.demo.webSocket.service;

import com.example.demo.entity.Email;
import com.example.demo.entity.Response;
import com.example.demo.util.ChConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class WebsocketService {

    private static final Logger logger = LoggerFactory.getLogger(WebsocketService.class);

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public Response send(@RequestBody Email mail){
        logger.info("get req msg: {}", mail.getEmail());

        String message = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now())
                + "向 --> " + mail.getEmail() + "发送了一封邮件";

        Response response = new Response(message);
        messagingTemplate.convertAndSend(ChConstants.WEBSOCKET_DESTINATION.getTopic(), response);

        logger.info("send ws msg: {}", response.toString());

        return response;
    }
}
