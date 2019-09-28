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

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lotus.backstage.user.model.User;
import com.lotus.core.base.baseexception.BaseException;
import com.lotus.core.base.returnmessage.IMessage;
import com.lotus.core.base.returnmessage.Message;
import com.lotus.core.concurrent.CurrentWxMiniCustomer;
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
		//String token = util.generateToken(201909190936028l, customerJson);
		
		//System.out.println(token);
	}
	
	@Test
	public void test1() {
		String token = "\"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJMT1RVUyIsImRhdGEiOiJ7XCJhZGRyZXNzXCI6XCLljJfkuqzluILljJfkuqzluILkuJzln47ljLpcIixcImJlZ2luVGltZVwiOm51bGwsXCJiaXJ0aGRheVwiOlwiMjAxNi0wOS0yNFwiLFwiYnJpZWZJbnRyb2R1Y3Rpb25cIjpudWxsLFwiY3JlYXRlQnlcIjpudWxsLFwiY3JlYXRlQnlOYW1lXCI6bnVsbCxcImNyZWF0ZVRpbWVcIjpudWxsLFwiZW5kVGltZVwiOm51bGwsXCJnZW9ncmFwaGljYWxQb3NpdGlvblwiOm51bGwsXCJoZWFkUG9ydHJhaXRcIjpudWxsLFwiaWRcIjoyMDE5MDkyNDE1MTUwNzIwLFwiaWRDYXJkXCI6bnVsbCxcImlkc1wiOm51bGwsXCJpbnRlZ3JhbFR5cGVJZFwiOm51bGwsXCJsb2dpblR5cGVcIjpudWxsLFwibW9iaWxlXCI6XCIxMzU5MzM4OTI1MVwiLFwibmlja05hbWVcIjpudWxsLFwibmlja05hbWUxXCI6bnVsbCxcIm9jY3VwYXRpb25cIjpcIuiHqueUseiBjOS4mlwiLFwicGFnZU51bWJlclwiOm51bGwsXCJwYWdlU2l6ZVwiOm51bGwsXCJwYXNzV29yZFwiOm51bGwsXCJzZXhcIjoxLFwidXBkYXRlQnlcIjpudWxsLFwidXBkYXRlQnlOYW1lXCI6bnVsbCxcInVwZGF0ZVRpbWVcIjpudWxsLFwidXNlUGFnZW5hdGlvblwiOnRydWUsXCJ1c2VyTmFtZVwiOm51bGwsXCJ2YWxpZFwiOjEsXCJ3eE1pbmlQcm9BdmF0YXJVcmxcIjpcImh0dHBzOi8vd3gucWxvZ28uY24vbW1vcGVuL3ZpXzMyL0UxSU8yV2Y4UndpYlpsQzVzRTdpY1l1NFh5ZVhiSUFqaWFXQkRnclFTNTFHQVRFN1hBSjRuaWJFNDVzTUljeENpY2ljekp3dG9kWjhaUzNneWZCMGd6ZFY2WmhBLzEzMlwiLFwid3hNaW5pUHJvQ29kZVwiOm51bGwsXCJ3eE1pbmlQcm9FbmNyeXB0ZURhdGFcIjpudWxsLFwid3hNaW5pUHJvSXZcIjpudWxsLFwid3hNaW5pUHJvT3BlbklkXCI6XCJvRmNYWDVicHhHZ1BXR0s3U3A3NkJlX1NWQU1RXCIsXCJ3eE1pbmlQcm9SYXdEYXRhXCI6bnVsbCxcInd4TWluaVByb1VuaW9uSWRcIjpudWxsLFwid3hNaW5pUHJvc2lnbmF0dXJlXCI6bnVsbCxcInd4U2Vlc2lvbktleVwiOm51bGx9IiwiaXNzIjoiTE9UVVMiLCJleHAiOjE1NjkzMTYwMzgsImlhdCI6MTU2OTMxMjQzOCwianRpIjoiMjAxOTA5MjQxNTE1MDcyMCJ9.d1k_IWecRdpjjl-mgAZfxSax5Qk08b4Q7frpwB8wPLY\"";
		try {
			///util.verify(token);
			
			Claim data = null;
	        	data = JWT.decode(token).getClaim("data");
	        	 if (data != null) {
	        		 Customer customer = null;
	        		 customer = baseJson.readValue(data.asString(),Customer.class);
	        		System.out.println(customer);
	             }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
