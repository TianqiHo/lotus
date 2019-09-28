package com.lotus.address;

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
import com.lotus.smallroutine.address.model.Address;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@AutoConfigureMockMvc
public class AddressTest {
	
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	public ObjectMapper baseJson;
	
	@Test
	public void testInsert() {
		
		Address address = new Address();
		address.setId(2019092611230443L);
		address.setAddressDetail("平安大街2号楼1");
		address.setAddressee("贺田奇1");
		address.setProvince(11);
		address.setProvinceName("河北省1");
		address.setCity(21);
		address.setCityName("石家庄1");
		address.setCountry(31);
		address.setCountryName("长安区1");
	//	address.setCreateBy(1l);
		address.setDefaultAddress(1);
		address.setMobile("131655738391");
		
		try {
			String addressJson = baseJson.writeValueAsString(address);
			System.out.println(addressJson);
			  
			MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.post("/clientAddress/saveOrUpdateAddress").contentType(MediaType.APPLICATION_JSON_UTF8)
			            .content(addressJson))
			            .andReturn();
			String content =mvcResult.getResponse().getContentAsString();
			  
			System.out.println(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testSelect() {
		
		try {
			Address address = new Address();
			address.setId(2019092611230443L);
			String addressJson = baseJson.writeValueAsString(address);
			System.out.println(addressJson);
			  
			MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.post("/clientAddress/selectAddress").contentType(MediaType.APPLICATION_JSON_UTF8)
			            .content(addressJson))
			            .andReturn();
			String content =mvcResult.getResponse().getContentAsString();
			  
			System.out.println(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testSelects() {
		
		try {
			Address address = new Address();
			//address.setId(2019092611230443L);
			address.setUsePagenation(true);
			address.setPageNumber(1);
			address.setPageSize(10);
			String addressJson = baseJson.writeValueAsString(address);
			System.out.println(addressJson);
			  
			MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.post("/clientAddress/selectAddresss").contentType(MediaType.APPLICATION_JSON_UTF8)
			            .content(addressJson))
			            .andReturn();
			String content =mvcResult.getResponse().getContentAsString();
			  
			System.out.println(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testDeletes() {
		
		try {
			Address address = new Address();
			address.setIds("2019092611230443");
			
			String addressJson = baseJson.writeValueAsString(address);
			System.out.println(addressJson);
			  
			MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.post("/clientAddress/deleteAddresss").contentType(MediaType.APPLICATION_JSON_UTF8)
			            .content(addressJson))
			            .andReturn();
			String content =mvcResult.getResponse().getContentAsString();
			  
			System.out.println(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
