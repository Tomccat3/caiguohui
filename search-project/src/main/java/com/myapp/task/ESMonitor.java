package com.myapp.task;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.ExecutorService;

/**
 * @author cgh
 */
@Component
public class ESMonitor {

    private static final String HEALTH_CHECK_API = "http://127.0.0.1:9200/_cluster/health";

    private static final String GREEN = "green";

    private static final String YELLOW = "yellow";

    private static final String RED = "red";

    private static final Logger logger = LoggerFactory.getLogger(ESMonitor.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JavaMailSender javaMailSender;

    @Scheduled(fixedDelay = 10000)
    public void healthCheck(){

        HttpClient httpClient = HttpClients.createDefault();

        HttpGet get = new HttpGet(HEALTH_CHECK_API);

        try {
            HttpResponse response = httpClient.execute(get);
            if (response.getStatusLine().getStatusCode() != HttpServletResponse.SC_OK){
                System.out.println("Can not access ES Service normally! Please Check the server.");
            }else {
                String body = EntityUtils.toString(response.getEntity(), "UTF-8");
                JsonNode result = objectMapper.readTree(body);
                String status = result.get("status").asText();
                String message = "";
                boolean flag = false;
                switch (status){
                    case GREEN :
                        message = "ES Server run normally";
                        //System.out.println("ES Server run normally");
                        flag = true;
                        break;
                    case YELLOW:
                        message = "ES Server gets status yellow! Please check the Server";
                        //System.out.println("ES Server gets status yellow! Please check the Server");
                        break;
                    case RED:
                        message = "ES get Problem!!!";
                        //System.out.println("ES get Problem!!!");
                        break;
                    default:
                        message = "Unknown status : " +status;
                        //    System.out.println("Unknown status : " +status);
                        break;
                }
                if (!flag){
                    this.sendMessage(message);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(String message){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("18317153556@163.com");
        simpleMailMessage.setTo("10989406@qq.com");
        simpleMailMessage.setSubject("【警告】ES服务预警");
        simpleMailMessage.setText(message);
        ExecutorService service = SingletonPool.getInstance();
        service.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    if (SingletonPool.getCount().get() == 2){
                        service.shutdown();
                        logger.info("the task is down");
                    }
                    logger.info("start send mail ");
                    logger.info("当前线程为： " + Thread.currentThread().getName());
                    javaMailSender.send(simpleMailMessage);
                    logger.info("发送成功...");
                }catch (Exception e){
                    logger.error("send failed " + e);
                }
            }
        });
    }
}
