package com.example.demo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.internet.MimeMessage;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

//异步发送
public class MailUtil {
    private Logger logger = LoggerFactory.getLogger(MailUtil.class);

    private ScheduledExecutorService service = Executors.newScheduledThreadPool(6);

    private final AtomicInteger count = new AtomicInteger(1);

    public void start(final JavaMailSender mailSender, final SimpleMailMessage message){
        service.execute(new Runnable() {
            @Override
            public void run() {
                try{
                    if (count.get() == 2){
                        service.shutdown();
                        logger.info("the task is down");
                    }
                    logger.info("start send email and the index is " + count);
                    mailSender.send(message);
                    logger.info("send success");
                }catch (Exception e){
                    logger.error("send failed", e);
                }
            }
        });
    }

    public void startHtml(final JavaMailSender mailSender, final MimeMessage message){
        service.execute(new Runnable() {
            @Override
            public void run() {
                try{
                    if (count.get() == 2){
                        service.shutdown();
                        logger.info("the task is down");
                    }
                    logger.info("start send email and the index is " + count);
                    mailSender.send(message);
                    logger.info("send success");
                }catch (Exception e){
                    logger.error("send failed", e);
                }
            }
        });
    }

}
