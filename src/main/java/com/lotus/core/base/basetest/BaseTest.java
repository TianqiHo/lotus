package com.lotus.core.base.basetest;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lotus.core.base.basejson.BaseJson;

public abstract class BaseTest<T> {
	
	@Autowired
	public ObjectMapper baseJson;

	protected abstract void saveUpdate() throws Exception;
	
	protected abstract void select() throws Exception;
	
	protected abstract void delete() throws Exception;
	
	protected abstract void selects() throws Exception;
}
