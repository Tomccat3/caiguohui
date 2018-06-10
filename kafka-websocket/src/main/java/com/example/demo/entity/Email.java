package com.example.demo.entity;

import java.io.Serializable;
import java.util.HashMap;

//@SuppressWarnings("serial")
public class Email implements Serializable {

    private static final long serialVersionUID = 1L;

    //必填参数
    private String email; //接收方邮件

    private String subject;//主题

    private String content;//邮件内容

    //选填
    private String template; //模板

    private HashMap<String, String> kvMap; //自定义参数

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public HashMap<String, String> getKvMap() {
        return kvMap;
    }

    public void setKvMap(HashMap<String, String> kvMap) {
        this.kvMap = kvMap;
    }

    @Override
    public String toString() {
        return "Email{" +
                "email='" + email + '\'' +
                ", subject='" + subject + '\'' +
                ", content='" + content + '\'' +
                ", template='" + template + '\'' +
                ", kvMap=" + kvMap +
                '}';
    }
}
