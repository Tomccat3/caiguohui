package com.example.demo;

import com.example.demo.entity.Email;
import com.example.demo.service.IMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.demo"})
public class DemoApplication implements CommandLineRunner {

	@Autowired
	IMailService iMailService;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public  void run(String... args) throws Exception{
		Email email;
		for(int i = 0; i < 1000; i++){
			email = new Email();
			email.setEmail("1944666666@qq.com");
			email.setSubject("牛逼" + i);
			email.setContent("吴彦祖你好");
			email.setTemplate("welcome");
			iMailService.sendQueue(email);
			//iMailService.send(email);
		}


	}

}
