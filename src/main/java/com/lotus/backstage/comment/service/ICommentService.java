package com.lotus.backstage.comment.service;

import com.lotus.backstage.comment.model.Comment;
import com.lotus.core.base.returnmessage.Message;

public interface ICommentService {

    Message saveOrUpdateComment(Comment comment)throws Exception;
	
	Message selectComment(Comment comment)throws Exception;
	
	Message selectComments(Comment comment)throws Exception;
	
	Message deleteByPrimaryKey(Comment comment)throws Exception;

	Message deleteComments(Comment comment)throws Exception;

	Message selectClientComments(Comment comment)throws Exception;
}
