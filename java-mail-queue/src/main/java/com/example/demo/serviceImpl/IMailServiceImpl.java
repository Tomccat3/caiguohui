package com.example.demo.serviceImpl;

import com.example.demo.entity.Email;
import com.example.demo.queue.MailQueue;
import com.example.demo.service.IMailService;
import com.example.demo.util.Constants;
import com.example.demo.util.MailUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.ResourceUtils;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Service
public class IMailServiceImpl implements IMailService {

    @Autowired
    private JavaMailSender mailSender; //执行者

    @Autowired
    private Configuration configuration;//freemarker

    @Autowired
    private SpringTemplateEngine templateEngine; //thymeleaf

    @Value("${spring.mail.username}")
    public String USER_NAME; //发送者

    @Override
    public void send(Email mail) throws Exception{
        MailUtil mailUtil = new MailUtil();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(USER_NAME);
        message.setTo(mail.getEmail());
        message.setSubject(mail.getSubject());
        message.setText(mail.getContent());
        mailUtil.start(mailSender, message);
    }

    @Override
    public void sendHtml(Email email) throws Exception{
        MailUtil mailUtil = new MailUtil();
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(USER_NAME);
        helper.setTo(email.getEmail());
        helper.setSubject(email.getSubject());
        helper.setText(
                "<html><body><img src=\"cid:springcloud\" ></body></html>",
                true);
        // 发送图片
        File file = ResourceUtils.getFile("classpath:static"
                + Constants.SF_FILE_SEPARATOR + "image"
                + Constants.SF_FILE_SEPARATOR + "springcloud.png");
        helper.addInline("springcloud", file);
        // 发送附件
        file = ResourceUtils.getFile("classpath:static"
                + Constants.SF_FILE_SEPARATOR + "file"
                + Constants.SF_FILE_SEPARATOR + "abc.zip");
        helper.addAttachment("intersting", file);
        mailUtil.startHtml(mailSender, message);

    }

    @Override
    public void sendFreemarker(Email mail) throws Exception{
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(USER_NAME);
        helper.setTo(mail.getEmail());
        helper.setSubject(mail.getSubject());
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("content", mail.getContent());
        Template template = configuration.getTemplate(mail.getTemplate()+".flt");
        String text = FreeMarkerTemplateUtils.processTemplateIntoString(
                template, model);
        helper.setText(text, true);
        mailSender.send(message);
    }

    @Override
    public void sendQueue(Email mail) throws Exception {
        MailQueue.getMailQueue().produce(mail);
    }

}
