package com.lotus.token;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lotus.backstage.user.model.User;
import com.lotus.core.base.baseexception.BaseException;
import com.lotus.core.base.returnmessage.IMessage;
import com.lotus.core.base.returnmessage.Message;
import com.lotus.smallroutine.customer.model.Customer;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@AutoConfigureMockMvc
public class Tokentest {
	
	@Autowired
	private com.lotus.core.jwt.JwtUtil util;
	
	@Autowired
	public ObjectMapper baseJson;
	
	@Autowired
	private IMessage message;
	
	
	@Test
	public void testBaseExcep(){
		
	}
	
	
	@Test
	public void test() {
		
		
		Customer customer = new Customer();
		customer.setId(201909190936028l);
		customer.setNickName("");
		String customerJson = null;
		try {
			customerJson = baseJson.writeValueAsString(customer);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		System.out.println(customerJson);
		String token = util.generateToken(201909190936028l, customerJson);
		
		System.out.println(token);
	}

}
