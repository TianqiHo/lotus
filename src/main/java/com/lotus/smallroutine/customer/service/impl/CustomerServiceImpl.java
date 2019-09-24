package com.lotus.smallroutine.customer.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lotus.backstage.integral.mapper.IntegralMapper;
import com.lotus.backstage.integral.mapper.IntegralTypeMapper;
import com.lotus.backstage.integral.model.Integral;
import com.lotus.backstage.integral.model.IntegralType;
import com.lotus.core.base.baseexception.BaseException;
import com.lotus.core.base.baseservice.AbstractService;
import com.lotus.core.base.returnmessage.Message;
import com.lotus.core.concurrent.CurrentWxMiniCustomer;
import com.lotus.core.jwt.JwtUtil;
import com.lotus.smallroutine.customer.mapper.CustomerMapper;
import com.lotus.smallroutine.customer.model.Customer;
import com.lotus.smallroutine.customer.service.ICustomerService;
import com.lotus.wx.minipro.WxMiniProAuthCodeResponse;
import com.lotus.wx.minipro.WxMiniProDecodeUserInfo;
import com.lotus.wx.minipro.WxMiniProJscode2Session;

@Service(value = "customerService")
@Transactional
public class CustomerServiceImpl extends AbstractService implements ICustomerService {
	
	@Autowired
	private CustomerMapper customerMapper;

	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private WxMiniProJscode2Session wxMiniProJscode2Session;
	
	@Autowired
	private WxMiniProDecodeUserInfo wxMiniProDecodeUserInfo;
	
	@Autowired
	private IntegralTypeMapper integralTypeMapper;
	
	@Autowired
	private IntegralMapper integralMapper;
	
	
	
	@Override
	public Message insertOrUpdate(Customer customer)throws Exception{
			Message	message = this.iMessage.buildDefaultMessage();
			/*switch (customer.getLoginType()) {
			case LG_MINIPRO:
				if(!StringUtils.isEmpty(CurrentWxMiniCustomer.obtainCustomerID())) {
					customer.setId(CurrentWxMiniCustomer.obtainCustomerID());
				}
				
				break;
			default:
				break;
			}*/
			if(StringUtils.isEmpty(customer.getId())) {
				customer.buildCreateDefaultValue();
				customerMapper.insertSelective(customer);
			}else {
				customer.buildUpdateDefaultValue();
				customerMapper.updateByPrimaryKeySelective(customer);
			}
			
			Integral isExisParam = new Integral();
			isExisParam.setServiceId(customer.getId());
			isExisParam.setIntegralTypeId(customer.getIntegralTypeId());
			List<Integral> integrals = integralMapper.selectIntegral(isExisParam);
            StringBuffer alertMessage = new StringBuffer("请求成功");
			if(null==integrals || (null!=integrals && integrals.size()==0)) {
				IntegralType integralType=integralTypeMapper.selectByPrimaryKey(customer.getIntegralTypeId());
				isExisParam.buildCreateDefaultValue();
				isExisParam.setCreateBy(customer.getId());
				isExisParam.setIntegral(integralType.getIntegralNum());
				isExisParam.setIntegralTypeId(customer.getIntegralTypeId());
				int num = integralMapper.insertSelective(isExisParam);
				if(num==1) {
					alertMessage.append(",并赠送了"+integralType.getIntegralNum()+"积分");
				}else {
					alertMessage.append(",未成功赠送积分");
				}
			}
			message = this.iMessage.buildSuccessMessage();
			message.setAlertMessage(alertMessage.toString());
			
		return message;
	}
	

	@Override
	public Message update(Customer user) {
		return null;
	}

	@Override
	public Message selectCustomer(Customer customer)throws Exception{
		Message	message = this.iMessage.buildDefaultMessage();
		if(StringUtils.isEmpty(customer.getId())) {
			customer.setId(CurrentWxMiniCustomer.obtainCustomerID());
		}
		
		if(null!=customer && !StringUtils.isEmpty(customer.getId())) {
			customer = customerMapper.selectByPrimaryKey(customer.getId());
			message = this.iMessage.buildSuccessMessage();
			message.setData(customer);
		}else {
			throw new BaseException("参数ID为空");
		}
		return message;
	}

	@Override
	public Message selects(Customer user) {
		return null;
	}

	/**
	 * 更新数据库用户的的微信信息
	 */
	@Override
	public Message updateWxCustomer(Customer customer) throws Exception {
		Message	message = this.iMessage.buildDefaultMessage();
		
		if(!StringUtils.isEmpty(customer.getWxMiniProRawData())) {
	        Map<String, Object> userInfoMap = objectMapper.readValue(customer.getWxMiniProRawData(), Map.class);
	        
			readWxUserInfoMap(userInfoMap,customer);
			int oprate = customerMapper.updateByPrimaryKeySelective(customer);
			message = iMessage.buildSuccessMessage();
			message.setData(oprate);
		}else {
			throw new BaseException("rowData无效");
		}
		return message;
	}
	
	private Customer readWxUserInfoMap(Map<String, Object> userInfoMap,Customer wxCustomerParam) {
		Iterator<Entry<String, Object>> enIterator =  userInfoMap.entrySet().iterator();
		 while(enIterator.hasNext()) {
			 Entry<String, Object>  entry = enIterator.next();
			logger.info(entry.getKey()+"------------------------------"+entry.getValue());
		 }
	        if(null!=userInfoMap) {
	        	if(userInfoMap.containsKey("unionId") && null!=userInfoMap.get("unionId")){
	        		wxCustomerParam.setWxMiniProUnionId(String.valueOf(userInfoMap.get("unionId")));
	        	}
				if(userInfoMap.containsKey("nickName") && null!=userInfoMap.get("nickName")){
					wxCustomerParam.setNickName(String.valueOf(userInfoMap.get("nickName")));
			    }
				if(userInfoMap.containsKey("avatarUrl") && null!=userInfoMap.get("avatarUrl")){
					//logger.info("------------------------------------------------"+String.valueOf(userInfoMap.get("avatarUrl")));
					wxCustomerParam.setWxMiniProAvatarUrl(String.valueOf(userInfoMap.get("avatarUrl")));
					wxCustomerParam.setHeadPortrait(String.valueOf(userInfoMap.get("avatarUrl")));
				}
				if(userInfoMap.containsKey("gender") && null!=userInfoMap.get("gender")){
					wxCustomerParam.setSex(Integer.valueOf(userInfoMap.get("gender").toString()));
				}
				
				StringBuffer address = new StringBuffer();
				if(userInfoMap.containsKey("country") && null!=userInfoMap.get("country")){
					address.append(userInfoMap.get("country"));
				}
				if(userInfoMap.containsKey("province") && null!=userInfoMap.get("province")){
					address.append(userInfoMap.get("province"));
				}
               if(userInfoMap.containsKey("city") && null!=userInfoMap.get("city")){
               	address.append(userInfoMap.get("city"));
				}
               wxCustomerParam.setGeographicalPosition(address.toString());
				
	         }
	        return wxCustomerParam;
	}
	@Override
	public Message login(Customer customer) throws Exception {
		Message	message = this.iMessage.buildLoginFailMessage();
		
		switch (customer.getLoginType()) {
		//小程序
		case LG_MINIPRO:
			if(StringUtils.isEmpty(customer.getWxMiniProCode())) {
				message.setAlertMessage("微信wxLogin的code无效，请重新登陆微信小程序");
				return message;
			}
			
			WxMiniProAuthCodeResponse wxMiniProAuthCodeResponse = wxMiniProJscode2Session.getWxSession(customer.getWxMiniProCode());
			Customer wxCustomerParam = new Customer();
			wxCustomerParam.setWxMiniProOpenId(wxMiniProAuthCodeResponse.getOpenid());
			Customer wxCustomer = customerMapper.selectCustomer(wxCustomerParam);
			
			if(null==wxCustomer) {
				Map<String,Object> userInfoMap = wxMiniProDecodeUserInfo.getUserInfo(
						customer.getWxMiniProEncrypteData(), 
						wxMiniProAuthCodeResponse.getSession_key(),
						customer.getWxMiniProIv());
				if(null==userInfoMap) {
					throw new BaseException("解密用户信息无效");
				}
				readWxUserInfoMap(userInfoMap, wxCustomerParam);
				wxCustomerParam.buildCreateDefaultValue();
				customerMapper.insertSelective(wxCustomerParam);
			}else {
				//wxCustomerParam.setId(CurrentWxMiniCustomer.obtainCustomerID());
				//customerMapper.updateByPrimaryKeySelective(wxCustomerParam);
			}
			
			try {
				String token = jwtUtil.generateToken(wxCustomerParam.getId(),objectMapper.writeValueAsString(wxCustomerParam));
				message = this.iMessage.buildLoginSuccessMessage();
				Map<String,Object> tokenMap = new HashMap<String,Object>(2) {{
					put("LOTUS",token);
					put("id",(wxCustomer==null?wxCustomerParam.getId():wxCustomer.getId()));
					//put("wxSessionKey",wxMiniProAuthCodeResponse.getSessionKey());
				}};
				message.setData(tokenMap);
			} catch (JsonProcessingException e1) {
				throw new BaseException(e1);
			}
			
			break;
			
		//小程序
		case LG_MOBILE_CODE:
			
			break;

		default:
			//手机密码
			Customer userName = new Customer();
			Customer user = customerMapper.selectCustomer(userName);
			if(null!=user) {
				if(user.getPassWord().equals(customer.getPassWord())) {
					message = this.iMessage.buildLoginSuccessMessage();
					try {
						jwtUtil.generateToken(user.getId(),objectMapper.writeValueAsString(user));
					} catch (JsonProcessingException e) {
						throw new BaseException(e);
					}
				}else {
					message.setRealMessage("密码错误");
				}
				
			}else {
				message.setRealMessage("用户名不存在");
			}
			break;
		}
		
		
		return message;
	}


	@Override
	public Message signIn(Customer customer) throws Exception {
		Message	message = this.iMessage.buildDefaultMessage();
		
		if(!StringUtils.isEmpty(customer.getIntegralTypeId())) {
			Integral isExisParam = new Integral();
			isExisParam.setServiceId(CurrentWxMiniCustomer.obtainCustomerID());
			isExisParam.setIntegralTypeId(customer.getIntegralTypeId());
			isExisParam.setBeginTime(new Date());
			List<Integral> integrals = integralMapper.selectIntegral(isExisParam);
	        StringBuffer alertMessage = new StringBuffer("请求成功");
	        
	        if(null==integrals || (null!=integrals && integrals.size()==0)) {
				IntegralType integralType=integralTypeMapper.selectByPrimaryKey(customer.getIntegralTypeId());
				isExisParam.buildCreateDefaultValue();
				isExisParam.setCreateBy(CurrentWxMiniCustomer.obtainCustomerID());
				isExisParam.setIntegral(integralType.getIntegralNum());
				isExisParam.setIntegralTypeId(customer.getIntegralTypeId());
				int num = integralMapper.insertSelective(isExisParam);
				if(num==1) {
					alertMessage.append(",并赠送了"+integralType.getIntegralNum()+"积分");
				}else {
					alertMessage.append(",未成功赠送积分");
				}
			}
	        message = iMessage.buildSuccessMessage();
	        message.setAlertMessage(alertMessage.toString());
		}else {
			throw new BaseException("参数integralTypeId为空");
		}
		return message;
	}


	@Override
	public Message isSignIn(Integral integral) throws Exception {
		Message	message = this.iMessage.buildDefaultMessage();
		integral.setServiceId(CurrentWxMiniCustomer.obtainCustomerID());
		integral.setBeginTime(new Date());
		if(!StringUtils.isEmpty(integral.getServiceId())) {
			if(StringUtils.isEmpty(integral.getIntegralTypeId())) {
				throw new BaseException("签到integralTypeId类型为空");
			}
			
			List<Integral> integrals = integralMapper.selectIntegral(integral);
			message = this.iMessage.buildSuccessMessage();
            if(null!=integrals && integrals.size()>=1) {
            	
            	message.setData(new HashMap<String,Object>(){
					private static final long serialVersionUID = 4432083183170943608L;
				{
            		put("isSignIn", true);
            		put("signInMsg", "今日已签到");
            	}});
            	
            	
			}else {
				
				message.setData(new HashMap<String,Object>(){
					private static final long serialVersionUID = 4432083183170943608L;

				{
            		put("isSignIn", false);
            	}});
			}
			
		}else {
			throw new BaseException("请重新登陆");
		}
		return message;
	}
}
