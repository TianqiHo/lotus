package com.lotus.smallroutine.follow.service;

import com.lotus.core.base.returnmessage.Message;
import com.lotus.smallroutine.follow.model.Follow;

public interface IFollowService {

	   Message saveOrUpdateFollow(Follow follow)throws Exception;
		
		Message selectFollow(Follow follow)throws Exception;
		
		Message selectFollows(Follow follow)throws Exception;

		Message deleteFollows(Follow follow)throws Exception;
}
