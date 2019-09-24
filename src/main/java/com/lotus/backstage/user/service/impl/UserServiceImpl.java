package com.lotus.backstage.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lotus.backstage.user.mapper.UserMapper;
import com.lotus.backstage.user.model.User;
import com.lotus.backstage.user.service.IUserService;
import com.lotus.core.base.baseexception.BaseException;
import com.lotus.core.base.baseservice.AbstractService;
import com.lotus.core.base.returnmessage.Message;
import com.lotus.core.jwt.JwtUtil;

@Service(value = "userService")
@Transactional
public class UserServiceImpl extends AbstractService implements IUserService {
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	/*@Autowired
	private Encryption encryption;
	*/
	//@Value("${lotus.defaultBackstageUserPassword}")
	//private String defaultBackstageUserPassword;

	@Override
	public Message insertOrUpdate(User user)throws Exception{
		Message	message = this.iMessage.buildDefaultMessage();
			if(StringUtils.isEmpty(user.getId())) {
				user.buildCreateDefaultValue();
				/*if(StringUtils.isEmpty(user.getPassWord())) {
					user.setPassWord(encryption.encryption(defaultBackstageUserPassword));
				}else {
					user.setPassWord(encryption.encryption(user.getPassWord()));
				}*/
				userMapper.insertSelective(user);
			}else {
				user.buildUpdateDefaultValue();
				userMapper.updateByPrimaryKeySelective(user);
			}
			
			message = this.iMessage.buildSuccessMessage();
		return message;
	}
	
	
	@Override
	public User select(User user)throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> selects(User user)throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Message login(User loginUser) throws Exception {
       Message	message = this.iMessage.buildLoginFailMessage();
		
		switch (loginUser.getLoginType()) {
		default:
			//手机密码
			User userName = new User();
			userName.setUserName(loginUser.getUserName());
			User user = userMapper.selectUser(userName);
			if(null!=user) {
				if(user.getPassWord().equals(loginUser.getPassWord())) {
			//	if(null!=user.getPassWord() && null!=loginUser.getPassWord() && encryption.matches(loginUser.getPassWord(), user.getPassWord())) {
					message = this.iMessage.buildLoginSuccessMessage();
					try {
						String token =jwtUtil.generateToken(user.getId(),objectMapper.writeValueAsString(user));
						message.setData(token);
					} catch (JsonProcessingException e) {
						throw new BaseException(e);
					}
				}else {
					throw new BaseException("密码错误");
				}
				
			}else {
				throw new BaseException("用户名不存在");
			}
			break;
		}
		
		return message;
	}

}
