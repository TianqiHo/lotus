package com.lotus.follow;

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
import com.lotus.smallroutine.follow.model.Follow;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@AutoConfigureMockMvc
public class FollowTest {

	
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	public ObjectMapper baseJson;
	
	@Test
	public void testSave() {
		
		Follow follow = new Follow();
		follow.setSerciceId(2019091911131454L);
		follow.setFollowType(2);
		
		try {
			String followJson = baseJson.writeValueAsString(follow);
			System.out.println(followJson);
			  
			MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.post("/clientFollow/saveOrUpdateFollow").contentType(MediaType.APPLICATION_JSON_UTF8)
			            .content(followJson))
			            .andReturn();
			String content =mvcResult.getResponse().getContentAsString();
			  
			System.out.println(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSelects() {
		
		Follow follow = new Follow();
		follow.setPageNumber(15);
		follow.setPageSize(1);
		follow.setUsePagenation(true);
		follow.setCategoryType("NEWS_TYPE");
		follow.setFollowType(1);
		
		try {
			String followJson = baseJson.writeValueAsString(follow);
			System.out.println(followJson);
			  
			MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.post("/clientFollow/selectFollows").contentType(MediaType.APPLICATION_JSON_UTF8)
			            .content(followJson))
			            .andReturn();
			String content =mvcResult.getResponse().getContentAsString();
			  
			System.out.println(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
