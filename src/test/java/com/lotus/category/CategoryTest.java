package com.lotus.category;

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
import com.lotus.backstage.category.model.Category;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@AutoConfigureMockMvc
public class CategoryTest {

	
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	public ObjectMapper baseJson;
	
	@Test
	public void insert() {
		Category category = new Category();
		category.setCategoryName("HEI,LILIANMAMA");
		category.setCategoryType("BBS_TYPE");
	}
	
	@Test
	public void selects() {
		Category category = new Category();
		category.setCategoryType("BBS_TYPE");
		category.setUsePagenation(true);
		category.setPageNumber(1);
		category.setPageSize(10);
		
		
		try {
			String bannerJson = baseJson.writeValueAsString(category);
			System.out.println(bannerJson);
			  
			MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.post("/clientCategory/selectCategorys").contentType(MediaType.APPLICATION_JSON_UTF8)
			            .content(bannerJson))
			            .andReturn();
			String content =mvcResult.getResponse().getContentAsString();
			  
			System.out.println(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
			  
	}
}
