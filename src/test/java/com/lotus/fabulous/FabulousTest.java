package com.lotus.fabulous;

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

import com.lotus.backstage.news.model.News;
import com.lotus.core.base.basetest.BaseTest;
import com.lotus.smallroutine.fabulous.model.Fabulous;


@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@AutoConfigureMockMvc
public class FabulousTest extends BaseTest<Fabulous> {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void test() {
		try {
			//saveUpdate();
			//fabulous();
			//selects();
			//select();
			cancelFabulous();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void cancelFabulous() {
			
			try {
				Fabulous fab = new Fabulous();
				fab.setServiceId(2019091814130473L);
				//fab.setCreateBy(1l);
				fab.setId(2019091819321197L);
				 String fabJson = baseJson.writeValueAsString(fab);
				  System.out.println(fabJson);
			        
				  MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.post("/clientFabulous/cancleFabulousSum").contentType(MediaType.APPLICATION_JSON_UTF8)
			                .content(fabJson))
			                .andReturn();
			      String content =mvcResult.getResponse().getContentAsString();
				  
				  System.out.println(content);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
	public void fabulous() {
		
		try {
			Fabulous fab = new Fabulous();
			fab.setServiceId(2019091814130473L);
			//fab.setCreateBy(1l);
			
			 String fabJson = baseJson.writeValueAsString(fab);
			  System.out.println(fabJson);
		        
			  MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.post("/clientFabulous/fabulousSum").contentType(MediaType.APPLICATION_JSON_UTF8)
		                .content(fabJson))
		                .andReturn();
		      String content =mvcResult.getResponse().getContentAsString();
			  
			  System.out.println(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void saveUpdate() throws Exception {
		/*Fabulous fabulous = new Fabulous();
		fabulous.setCreateBy(1l);
		fabulous.setServiceId(20190915113159l);
		fabulous.setType(2);//对评论点赞
		
		FabulousSeeSum fabulousSeeSum =new FabulousSeeSum();
		fabulousSeeSum.setCommentId(20190915113159l);*/
		try {
			News news = new News();
			news.setId(2019091812142664L);
			
			 String commentJson = baseJson.writeValueAsString(news);
			  
		        
			  MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.post("/clientNews/seeSum")
					    .contentType(MediaType.APPLICATION_JSON_UTF8)
		                .content(commentJson))
		                .andReturn();
		      String content =mvcResult.getResponse().getContentAsString();
			  
			  System.out.println(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
	

	@Override
	protected void select() throws Exception {
		try {
			Fabulous fab = new Fabulous();
			fab.setServiceId(20190915113159l);
			fab.setCreateBy(1l);
			
			 String fabJson = baseJson.writeValueAsString(fab);
			  
		        
			  MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.post("/clientFabulous/selectFabulous").contentType(MediaType.APPLICATION_JSON_UTF8)
		                .content(fabJson))
		                .andReturn();
		      String content =mvcResult.getResponse().getContentAsString();
			  
			  System.out.println(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	protected void delete() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void selects() throws Exception {
		// TODO Auto-generated method stub
		
	}

}
