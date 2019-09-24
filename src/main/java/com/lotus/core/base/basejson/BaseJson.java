package com.lotus.core.base.basejson;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.lotus.core.base.basemodel.BaseModel;


//@Component
public class BaseJson extends ObjectMapper{

	private static final long serialVersionUID = 8211504905578182484L;
	
	/**
	@Autowired
	private ObjectMapper objectMapper;
	
	private static Set<String> set =  new HashSet<String>() {{
			add("usePagenation");
		}};
		
	 public void addFilterForMapper(ObjectMapper mapper) {
	
	        SimpleBeanPropertyFilter fieldFilter = SimpleBeanPropertyFilter.serializeAllExcept(set);
	        SimpleFilterProvider filterProvider = new SimpleFilterProvider().addFilter("fieldFilter", fieldFilter);
	        objectMapper.setFilterProvider(filterProvider).addMixIn(BaseModel.class, FieldFilterMixIn.class);
	    }

    
    @JsonFilter("fieldFilter")
    interface FieldFilterMixIn{
    }
	**/
}
