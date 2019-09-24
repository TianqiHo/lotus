package com.lotus.comment;

import java.nio.charset.Charset;

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

import com.lotus.backstage.comment.model.Comment;
import com.lotus.core.base.basetest.BaseTest;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@AutoConfigureMockMvc
public class CommentTest extends BaseTest<Comment>{
	
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void test() {
		try {
			 //saveUpdate();
			//selects();
			selectClients();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void saveUpdate() throws Exception{
		   Comment comment = new Comment();
		  // comment.setId(20190915093335l);
		   comment.setArticalId(2019091812142664L);
		   comment.setSayWhat1("TONGYI文职敏是个坏人死变态啦啦啦啦请举手".getBytes(Charset.defaultCharset()));
		   comment.setCategoryType("NEWS_TYPE");
		   comment.setStatus(1);
		   comment.buildCreateDefaultValue();
		 //  comment.setCreateBy(1l);
		   comment.setIntegralTypeId(20190917224632L);
		   
		   String commentJson = baseJson.writeValueAsString(comment);
		  System.out.println(commentJson);
	        
		  MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.post("/clientComment/saveOrUpdateComment").contentType(MediaType.APPLICATION_JSON_UTF8)
	                .content(commentJson))
	                .andReturn();
	      String content =mvcResult.getResponse().getContentAsString();
		  
		  System.out.println(content);
	}

	@Override
	protected void select()throws Exception{
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void delete()throws Exception{
		// TODO Auto-generated method stub
		
	}

	
	@Override
	protected void selects()throws Exception{
		  Comment comment = new Comment();
		  comment.setUsePagenation(true);
		  comment.setPageNumber(1);
		  comment.setPageSize(15);
		  comment.setArticalId(2019091812131245L);
		  comment.setCategoryType("NEWS_TYPE");
		  String commentJson = baseJson.writeValueAsString(comment);
		 
	        
		  MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.post("/comment/selectComments").contentType(MediaType.APPLICATION_JSON_UTF8)
	                .content(commentJson))
	                .andReturn();
	      String content =mvcResult.getResponse().getContentAsString();
		  
		  System.out.println(content);
		
	}
	
	public void selectClients()throws Exception{
		  Comment comment = new Comment();
		  comment.setUsePagenation(true);
		  comment.setPageNumber(1);
		  comment.setPageSize(15);
		  comment.setArticalId(2019091812131245L);
		  comment.setCategoryType("NEWS_TYPE");
		  
		 // comment.setCreateBy(1L);
		  String commentJson = baseJson.writeValueAsString(comment);
		  System.out.println(commentJson);
	        
		  MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.post("/clientComment/selectComments").contentType(MediaType.APPLICATION_JSON_UTF8)
	                .content(commentJson))
	                .andReturn();
	      String content =mvcResult.getResponse().getContentAsString();
		  
		  System.out.println(content);
		
	}

	
}
