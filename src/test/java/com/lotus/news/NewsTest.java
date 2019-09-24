package com.lotus.news;

import java.nio.charset.Charset;
import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lotus.backstage.news.model.News;
import com.lotus.backstage.news.model.News2Category;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@AutoConfigureMockMvc
public class NewsTest {
	
	
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	public ObjectMapper baseJson;
	
	
	@Test
	public void testInsert() {
		  News news = new News();
		  //news.setCategoryId(2019091819484778L);
		  //news.setCategoryType("BBS_TYPE");
		 // news.setIntegralTypeId();
		  News2Category n2c= new News2Category();
		  n2c.setCategoryId(2019091819484778L);
		  news.setCategorys(new ArrayList<News2Category>() {{
			  add(n2c);
		  }});
		  news.setPlatForm(2);
		  news.setId(2019091820511643L);
		  news.setNewsContent("我在论坛一哥这发了个帖*********子！！！！！".getBytes(Charset.defaultCharset()));
	      news.setNewsImage("D:/UPLOAD/1.jpg,D:/UPLOAD/2.jpg");
	      
	      try {
				String newsJson = baseJson.writeValueAsString(news);
				System.out.println(newsJson);
				  
				MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.post("/clientNews/saveOrUpdateNews").contentType(MediaType.APPLICATION_JSON_UTF8)
				            .content(newsJson))
				            .andReturn();
				String content =mvcResult.getResponse().getContentAsString();
				  
				System.out.println(content);
			} catch (Exception e) {
				e.printStackTrace();
			}
				  
	}
	
	@Test
	public void testSelectNewss() {
		
        News news = new News();
        
        news.setCategoryType("NEWS_TYPE");//BBS_TYPE
        
        //news.setUsePagenation(true);
       // news.setPageNumber(1);
       // news.setPageSize(2);
       /// news.setValid(1);
        
        try {
			String newsJson = baseJson.writeValueAsString(news);
			System.out.println(newsJson);
			  
			MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.post("/clientNews/selectNewss").contentType(MediaType.APPLICATION_JSON_UTF8)
			            .content(newsJson))
			            .andReturn();
			String content =mvcResult.getResponse().getContentAsString();
			  
			System.out.println(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
			  
		  
	}

	
	@Test
	public void selectDetail() {
		 News news = new News();
		 news.setId(2019091812131245L);
		
		 try {
				String newsJson = baseJson.writeValueAsString(news);
				System.out.println(newsJson);
				  
				MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.post("/clientNews/selectNews").contentType(MediaType.APPLICATION_JSON_UTF8)
				            .content(newsJson))
				            .andReturn();
				String content =mvcResult.getResponse().getContentAsString();
				  
				System.out.println(content);
			} catch (Exception e) {
				e.printStackTrace();
			}
				  
	}
	
	@Test
	public void testShare() {
		
		News news = new News();
		news.setCategoryType("NEWS_TYPE");
		news.setId(2019091812142664L);
		news.setIntegralTypeId(2019092011571139L);
		

		 try {
				String newsJson = baseJson.writeValueAsString(news);
				System.out.println(newsJson);
				  
				MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.post("/clientNews/shareNews").contentType(MediaType.APPLICATION_JSON_UTF8)
				            .content(newsJson))
				            .andReturn();
				String content =mvcResult.getResponse().getContentAsString();
				  
				System.out.println(content);
			} catch (Exception e) {
				e.printStackTrace();
			}
				  
	}
}
