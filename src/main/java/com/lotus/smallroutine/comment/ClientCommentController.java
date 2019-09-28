package com.lotus.smallroutine.comment;

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
import com.lotus.core.concurrent.CurrentWxMiniCustomer;
import com.lotus.core.jwt.annotation.ClientRequireLogin;

@RestController
@RequestMapping("clientComment")
public class ClientCommentController extends BaseController<Comment> {

	@Autowired
	private ICommentService commentService;
	
	@Override
	protected Class<?> getClassForLogger() {
		return ClientCommentController.class;
	}
	
	 /**
	   * 分页查询Comment
	 * @param json
	 * @return
	 */
	@ClientRequireLogin
	@RequestMapping(method = {RequestMethod.POST,RequestMethod.GET},value = "/selectComments")
	public Message selectClientComments(@RequestBody Comment comment) {
		this.logger.info("-----------selectClientComments()开始执行--------------");
		this.logger.info("参数是{}",comment.toString());
		
		Message message = null;

		try {
			message = commentService.selectClientComments(comment);
		} catch (Exception e) {
			message=setException(message, e, "selectClientComments()");
		}
		this.logger.info("-----------selectClientComments()执行结束--------------");
		printResult(message);
		return message;
	}
	
	/**
	 * 新增修改Comment
	 * @param json
	 * @return
	 */
	@ClientRequireLogin
	@RequestMapping(method = {RequestMethod.POST,RequestMethod.GET},value = "/saveOrUpdateComment")
	public Message saveOrUpdateComment(@RequestBody Comment json) {
		this.logger.info("-----------saveOrUpdateComment()开始执行--------------");
		this.logger.info("参数是{}",json.toString());
		
		Message message = null;

		try {
			if(StringUtils.isEmpty(json.getId())) {
				json.setCreateBy(CurrentWxMiniCustomer.obtainCustomerID());
			}else {
				json.setUpdateBy(CurrentWxMiniCustomer.obtainCustomerID());
			}
	
			message = commentService.saveOrUpdateComment(json);
		} catch (Exception e) {
			message=setException(message, e, "saveOrUpdateComment()");
		}
		this.logger.info("-----------saveOrUpdateComment()执行结束--------------");
		printResult(message);
		return message;
	}
	
}
