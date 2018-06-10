package com.example.demo.service;

import com.example.demo.entity.Email;

public interface IMailService {

    void send(Email mail)throws Exception;

    void sendHtml(Email email)throws Exception;

    void sendFreemarker(Email mail)throws Exception;

    void sendQueue(Email mail) throws Exception;

}
