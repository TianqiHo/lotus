package com.lotus.customer;

import java.util.Calendar;

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
import com.lotus.core.base.constants.Constants.LoginType;
import com.lotus.smallroutine.customer.model.Customer;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@AutoConfigureMockMvc
public class CustomerTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	public ObjectMapper baseJson;
	
	@Test
	public void testSaveUpdate() {
		Customer customer = new Customer();
		//customer.setId(201909172334537539L);
		customer.setAddress("河北省保定市涞水县东文山乡南兵上村1号1");
		Calendar ca = Calendar.getInstance();
		ca.set(1991, 02, 05);
		customer.setBirthday(ca.getTime());
		customer.setBriefIntroduction("我就是我，是颜色不一样的烟火2");
		customer.setGeographicalPosition("中国北京2");
		customer.setHeadPortrait("d:/lotusfile/upload2");
		customer.setIdCard("130623199103052718");
		customer.setMobile("131655738391");
		customer.setNickName("HTQ1");
		customer.setUserName("hetianqi1");
		customer.setSex(2);
		customer.setPassWord("htq2");
		customer.setOccupation("IT2");
		customer.setIntegralTypeId(20190917224429L);
		customer.setWxMiniProAvatarUrl("5");
		customer.setWxMiniProOpenId("7");
		customer.setWxMiniProUnionId("0");
		
	try {
		String customerJson = baseJson.writeValueAsString(customer);
		System.out.println(customerJson);
		  
		MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.post("/customer/saveOrUpdateCustomer").contentType(MediaType.APPLICATION_JSON_UTF8)
		            .content(customerJson))
		            .andReturn();
		String content =mvcResult.getResponse().getContentAsString();
		  
		System.out.println(content);
	} catch (Exception e) {
		e.printStackTrace();
	}
	}

	
	@Test
	public void testSelect() {
		
		Customer customer = new Customer();
		customer.setId(201909172341032455L);
		
		
		try {
			String customerJson = baseJson.writeValueAsString(customer);
			System.out.println(customerJson);
			  
			MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.post("/customer/selectCustomer").contentType(MediaType.APPLICATION_JSON_UTF8)
			            .content(customerJson))
			            .andReturn();
			String content =mvcResult.getResponse().getContentAsString();
			  
			System.out.println(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testLogin() {
		try {
			
			Customer customer = new Customer();
			customer.setLoginType(LoginType.LG_MINIPRO);
			customer.setWxMiniProCode("0235Ldh003lXoI1uEXd00JOAh005LdhM");
			
			customer.setWxMiniProEncrypteData("q3ppAn3YHRyHFtjl8CtfbWqSFFXQnaVgZQN16I9Sh4USEchGHZ+f3OqwI5sxKooHwlqP6FzFREnxB8hG72dnDyYeIBv7GdcnulPbH927wOaPmVhs3P3+8WEeROl5Zq5Rb7euOuEgABhxAdVQJjhc7gv4VtfQ4V21qCncJWw5pNgKz88T3tkhPeBfOCvoar4TnastQp4rO9JayJJA0wOPRKwMB5nBTHeHDHYQy1Yp3j+0yvZmfl5g2m8A0iwZTkNJiJU9ipiLJAoeCcpuwLUDXedUcnFOWbINe7imfyJCEEwcK9TmC/4AaigwrqhxdDwAP0gw9ke8OIpv3hkWNyG6J8q7HeN6rwQRcZHdAXoJQPiFJvZSfVW6csPt/3rKQkOHGDAMc6CNqt4oGex08tLpsN3SXDYrTn5VmzpzC+efF/o9yyThU9PGaxB4NdQjDInjZprxck9Q6lnXkoL5Bboeey7vgyKJBGF5EOijuLWM1vM=");
				
			customer.setWxMiniProIv("HHV9heASInDVMSrmirV2bg==");
			
			customer.setWxMiniProsignature("d04ce7f33d3cc49ada2d651a239e7c108b377c9a");
			
			String json = baseJson.writeValueAsString(customer);
			MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.post("/customer/login").contentType(MediaType.APPLICATION_JSON_UTF8)
			            .content(json))
			            .andReturn();
			String content =mvcResult.getResponse().getContentAsString();
			  
			System.out.println(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
