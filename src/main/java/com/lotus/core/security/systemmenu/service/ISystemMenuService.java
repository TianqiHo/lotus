package com.lotus.core.security.systemmenu.service;

import com.lotus.core.base.returnmessage.Message;
import com.lotus.core.security.systemmenu.model.SystemMenu;

public interface ISystemMenuService {

	Message saveOrupdateSystemMenu(SystemMenu systemMenu)throws Exception;
	Message deleteSystemMenus(SystemMenu systemMenu)throws Exception;
	Message selectSystemMenus(SystemMenu systemMenu)throws Exception;
	Message selectSystemMenu(SystemMenu systemMenu)throws Exception;
}
