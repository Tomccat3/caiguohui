package com.example.demo.mailService;

import com.example.demo.entity.Email;

public interface IMailService {

    boolean send(Email mail)throws Exception;

    void sendHtml(Email email)throws Exception;

    void sendFreemarker(Email mail)throws Exception;

    void sendQueue(Email mail) throws Exception;

}
