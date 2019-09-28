package com.lotus.product;

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
import com.lotus.backstage.product.model.Product;
import com.lotus.smallroutine.address.model.Address;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@AutoConfigureMockMvc
public class ProductTest {
	
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	public ObjectMapper baseJson;
	
	@Test
	public void testSelectClients() {
		Product product = new Product();
		product.setUsePagenation(true);
		product.setPageNumber(1);
		product.setPageSize(10);
		
		try {
			String productJson = baseJson.writeValueAsString(product);
			System.out.println(productJson);
			  
			MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.post("/clientProduct/selectProducts").contentType(MediaType.APPLICATION_JSON_UTF8)
			            .content(productJson))
			            .andReturn();
			String content =mvcResult.getResponse().getContentAsString();
			  
			System.out.println(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSelect() {
		Product product = new Product();
		product.setId(2l);
		
		try {
			String productJson = baseJson.writeValueAsString(product);
			System.out.println(productJson);
			  
			MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.post("/clientProduct/selectProduct").contentType(MediaType.APPLICATION_JSON_UTF8)
			            .content(productJson))
			            .andReturn();
			String content =mvcResult.getResponse().getContentAsString();
			  
			System.out.println(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
