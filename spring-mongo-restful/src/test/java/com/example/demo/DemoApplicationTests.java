package com.example.demo;

import com.example.demo.entity.Notice;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class DemoApplicationTests {
	@Autowired
	private WebApplicationContext context;

	private MockMvc mvc;

	@Before
	public void setUp() throws Exception {
		mvc = MockMvcBuilders.webAppContextSetup(context).build();  //构造MockMvc

	}

	//插入操作测试
	@Test
	public void testSave() throws Exception {
		//构造对象信息
		Notice notice = new Notice();
		notice.setSiteId(2);
		notice.setCreator("allen");
		notice.setTitle("test");
		notice.setContent("for mock test");
		ObjectMapper mapper = new ObjectMapper();

		RequestBuilder request = null;
		String requestBody = "{\"siteId\":2, \"creator\":\"allen\", \"title\":\"test\", \"content\":\"for test\"}";

		mvc.perform(post("/save") // //调用接口
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(requestBody)
				.accept(MediaType.APPLICATION_JSON_UTF8)) //执行请求
        		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)) //验证响应contentType
				//使用jsonPath解析返回值，判断具体的内容
				.andReturn().getResponse().getContentAsString();
				//.andExpect(jsonPath("$.data.creator").value("allen"));


	}

	//查询操作测试
	@Test
	public void testGet() throws Exception{
		mvc.perform(get("/getOne/1")
				.accept(MediaType.APPLICATION_JSON_UTF8))

				.andExpect(status().isOk())
				.andExpect(content().string(containsString("皮皮怪")))
				.andExpect(jsonPath("$.content").value("皮皮怪"));

	}

	//删除操作测试
	@Test
	public void testDelete() throws Exception{
		mvc.perform(delete("/remove/2")
				.accept(MediaType.APPLICATION_JSON_UTF8))

				.andExpect(status().isOk());
	}


}
