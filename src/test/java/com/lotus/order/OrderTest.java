package com.lotus.order;

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
import com.lotus.backstage.order.model.Order;
import com.lotus.backstage.order.model.OrderStatus;


@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@AutoConfigureMockMvc
public class OrderTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	public ObjectMapper baseJson;
	
	
	@Test
	public void testSave() {
		
		Order order = new Order();
		order.setAddressId(2019092621054544L);
		order.setProductId(3L);
		
		try {
			String orderJson = baseJson.writeValueAsString(order);
			System.out.println(orderJson);
			 /*
			MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.post("/clientOrder/saveOrUpdateOrder").contentType(MediaType.APPLICATION_JSON_UTF8)
			            .content(orderJson))
			            .andReturn();
			String content =mvcResult.getResponse().getContentAsString();
			  
			System.out.println(content);*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testSelects() {
		
		Order order = new Order();
	    order.setStatus(1);
	    order.setUsePagenation(true);
	    order.setPageNumber(1);
	    order.setPageSize(10);
		
		try {
			String orderJson = baseJson.writeValueAsString(order);
			System.out.println(orderJson);
			  
			MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.post("/clientOrder/selectOrders").contentType(MediaType.APPLICATION_JSON_UTF8)
			            .content(orderJson))
			            .andReturn();
			String content =mvcResult.getResponse().getContentAsString();
			  
			System.out.println(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testUpdateStatus() {
		OrderStatus os = new OrderStatus();
		os.setOrderId(2019092709302831L);
		os.setStatus(2);
		
		try {
			String orderJson = baseJson.writeValueAsString(os);
			System.out.println(orderJson);
			  
			MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.post("/clientOrder/updateOrderStutus").contentType(MediaType.APPLICATION_JSON_UTF8)
			            .content(orderJson))
			            .andReturn();
			String content =mvcResult.getResponse().getContentAsString();
			  
			System.out.println(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testDeleteOrders() {
		Order os = new Order();
		os.setIds("2019092612424472");
		
		try {
			String orderJson = baseJson.writeValueAsString(os);
			System.out.println(orderJson);
			  
			MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.post("/clientOrder/deleteOrders").contentType(MediaType.APPLICATION_JSON_UTF8)
			            .content(orderJson))
			            .andReturn();
			String content =mvcResult.getResponse().getContentAsString();
			  
		//	System.out.println(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
