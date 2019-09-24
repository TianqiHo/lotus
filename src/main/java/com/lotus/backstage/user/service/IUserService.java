package com.lotus.backstage.user.service;

import java.util.List;

import com.lotus.backstage.user.model.User;
import com.lotus.core.base.returnmessage.Message;

public interface IUserService {
	
	Message insertOrUpdate(User user)throws Exception;
	
	User select(User user)throws Exception;
	
	List<User> selects(User user)throws Exception;
	
	Message login(User user)throws Exception;

}
