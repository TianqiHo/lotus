package com.lotus.backstage.comment.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lotus.backstage.comment.model.Comment;
import com.lotus.backstage.comment.service.ICommentService;
import com.lotus.core.base.basecontroller.BaseController;
import com.lotus.core.base.returnmessage.Message;
import com.lotus.core.concurrent.CurrentBackstageUser;
import com.lotus.core.jwt.annotation.ServerRequireLogin;

@RestController
@RequestMapping("comment")
public class CommentController extends BaseController<Comment> {

	@Autowired
	private ICommentService commentService;
	
	
	@Override
	protected Class<?> getClassForLogger() {
		return CommentController.class;
	}

	/**
	 * 新增修改Comment
	 * @param comment
	 * @return
	 */
	@ServerRequireLogin
	@RequestMapping(method = {RequestMethod.POST,RequestMethod.GET},value = "/saveOrUpdateComment")
	public Message saveOrUpdateComment(@RequestBody Comment comment) {
		this.logger.info("-----------saveOrUpdateComment()开始执行--------------");
		this.logger.info("参数是{}",comment.toString());
		
		Message message = null;

		try {
			
			if(!StringUtils.isEmpty(comment.getStatus())){
				comment.setExamineBy(CurrentBackstageUser.obtainUserID());
				comment.setExamineTime(new Date());
			}
			
			message = commentService.saveOrUpdateComment(comment);
		} catch (Exception e) {
			message=setException(message, e, "saveOrUpdateComment()");
		}
		this.logger.info("-----------saveOrUpdateComment()执行结束--------------");
		printResult(message);
		return message;
	}
	
	/**
	 * 分页查询Comment
	 * @param comment
	 * @return
	 */
	@ServerRequireLogin
	@RequestMapping(method = {RequestMethod.POST,RequestMethod.GET},value = "/selectComments")
	public Message selectComments(@RequestBody Comment comment) {
		this.logger.info("-----------selectComments()开始执行--------------");
		this.logger.info("参数是{}",comment.toString());
		
		Message message = null;

		try {
			message = commentService.selectComments(comment);
		} catch (Exception e) {
			message=setException(message, e, "selectComments()");
		}
		this.logger.info("-----------selectComments()执行结束--------------");
		printResult(message);
		return message;
	}
	
	@ServerRequireLogin
	@RequestMapping(method = {RequestMethod.POST,RequestMethod.GET},value = "/deleteComments")
	public Message deleteComments(@RequestBody Comment comment) {
		this.logger.info("-----------deleteComments()开始执行--------------");
		this.logger.info("参数是{}",comment.toString());
		
		Message message = null;

		try {
			message = commentService.deleteComments(comment);
		} catch (Exception e) {
			message=setException(message, e, "deleteComments()");
		}
		this.logger.info("-----------deleteComments()执行结束--------------");
		printResult(message);
		return message;
	}
	
	
	@ServerRequireLogin
	@RequestMapping(method = {RequestMethod.POST,RequestMethod.GET},value = "/selectComment")
	public Message selectComment(@RequestBody Comment comment) {
		this.logger.info("-----------selectComment()开始执行--------------");
		this.logger.info("参数是{}",comment.toString());
		
		Message message = null;

		try {
			message = commentService.selectComment(comment);
		} catch (Exception e) {
			message=setException(message, e, "selectComment()");
		}
		this.logger.info("-----------selectComment()执行结束--------------");
		printResult(message);
		return message;
	}
}
