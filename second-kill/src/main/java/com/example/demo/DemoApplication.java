package com.example.demo;

import com.example.demo.kafka.ConsumerGroup;
import com.example.demo.utils.SpringBeanFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.system.ApplicationPidFileWriter;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.mapper.autoconfigure.MapperAutoConfiguration;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableTransactionManagement//开启事务管理
@ServletComponentScan
@EnableAutoConfiguration//(exclude = {MapperAutoConfiguration.class})
@MapperScan(basePackages = "com.example.demo.dao")
@ComponentScan("com.example.demo")
public class DemoApplication {

	private static Logger LOGGER = LoggerFactory.getLogger(DemoApplication.class);

	public static void main(String[] args) {
//		SpringApplication.run(DemoApplication.class, args);
		new SpringApplicationBuilder(DemoApplication.class).
				listeners(new ApplicationPidFileWriter()).
				web(true).run(args);

		ConsumerGroup consumerGroup = SpringBeanFactory.getBean(ConsumerGroup.class);
		consumerGroup.excute();
		LOGGER.info("启动成功...");
	}
}
