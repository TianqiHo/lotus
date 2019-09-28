package com.lotus.backstage.order.service;

import java.util.List;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lotus.backstage.integral.mapper.IntegralMapper;
import com.lotus.backstage.integral.model.Integral;
import com.lotus.backstage.logistics.mapper.LogisticsDeleteBackupMapper;
import com.lotus.backstage.logistics.mapper.LogisticsMapper;
import com.lotus.backstage.logistics.model.Logistics;
import com.lotus.backstage.logistics.model.LogisticsDeleteBackup;
import com.lotus.backstage.order.mapper.OrderDeleteBackupMapper;
import com.lotus.backstage.order.mapper.OrderMapper;
import com.lotus.backstage.order.mapper.OrderStatusDeleteBackupMapper;
import com.lotus.backstage.order.mapper.OrderStatusMapper;
import com.lotus.backstage.order.model.Order;
import com.lotus.backstage.order.model.OrderDeleteBackup;
import com.lotus.backstage.order.model.OrderStatus;
import com.lotus.backstage.order.model.OrderStatusDeleteBackup;
import com.lotus.backstage.product.mapper.ProductMapper;
import com.lotus.backstage.product.model.Product;
import com.lotus.core.base.BaseRandom;
import com.lotus.core.base.baseexception.BaseException;
import com.lotus.core.base.baseservice.AbstractService;
import com.lotus.core.base.returnmessage.Message;
import com.lotus.core.concurrent.CurrentWxMiniCustomer;

@Service
@Transactional
public class OrderServiceImpl extends AbstractService implements IOrderService {

	@Override
	protected Class<?> getClassForLogger() {
		return OrderServiceImpl.class;
	}
	
	@Autowired
	private OrderMapper orderMapper;
	
	@Autowired
	private OrderStatusMapper orderStatusMapper;
	
	@Autowired
	private IntegralMapper integralMapper;
	
	@Autowired
	private ProductMapper productMapper;
	
	@Autowired
	private OrderDeleteBackupMapper orderDeleteBackupMapper;
	
	@Autowired
	private OrderStatusDeleteBackupMapper orderStatusDeleteBackupMapper;
	
	@Autowired
	private LogisticsMapper logisticsMapper;
	
	@Autowired
	private LogisticsDeleteBackupMapper logisticsDeleteBackupMapper;
	
	@Autowired
	private BaseRandom random;
	
	/**
	 * 生成订单、订单状态、抠库存、抠积分
	 */
	@Override
	public Message saveOrUpdateOrder(Order order) throws Exception {
		Message	message = this.iMessage.buildDefaultMessage();
		Long customerId = CurrentWxMiniCustomer.obtainCustomerID();
		Integer customerIntegralSum = integralMapper.selectIntegralSum(customerId);
		if(null!=customerIntegralSum) {
			Product product = productMapper.selectByPrimaryKey(order.getProductId());
			
			if(null!=product) {
				if(null==product.getProductRepertoryNum() || 0 == product.getProductRepertoryNum()) {
					throw new BaseException("库存不足");
				}
			}else {
				throw new BaseException("商品不存在");
			}
			
            synchronized (product.getProductRepertoryNum()) {
            	Integer transactionPrice = product.getProductIntegralPrice();
    			
    			if(transactionPrice<=customerIntegralSum) {
    				Integral integral = new Integral();
    				integral.setIntegral(-transactionPrice);
    				integral.setIntegralTypeId(2019092608024649L);//代表兑换商品
    				integral.setCreateBy(customerId);
    				integral.buildCreateDefaultValue();
    				integral.setServiceId(customerId);
    				integral.setLog("用于购买商品["+product.getId()+"]["+product.getProductName()+"]");
    				integralMapper.insertSelective(integral);
    				
    				int oprate;
    				if(StringUtils.isEmpty(order.getId())) {
    					order.buildCreateDefaultValue();
    					order.setCreateBy(customerId);
    					order.setOrderCode(String.valueOf(System.currentTimeMillis())+random.generateCode());
    					System.out.println(order.getOrderCode());
    					
    					oprate = orderMapper.insertSelective(order);
    				}else {
    					order.buildUpdateDefaultValue();
    					order.setUpdateBy(CurrentWxMiniCustomer.obtainCustomerID());
    					oprate = orderMapper.updateByPrimaryKeySelective(order);
    				}
    				
    			    OrderStatus	orderStatus = new OrderStatus();
    			    orderStatus.setOrderId(order.getId());
    			    orderStatus.setStatus(1);
    			    orderStatus.buildCreateDefaultValue();
    			    orderStatus.setCreateBy(customerId);
    				
    			    orderStatusMapper.insertSelective(orderStatus);
    			    
    			    Order updateStateId =  new Order();
    			    updateStateId.setId(order.getId());
    			    updateStateId.setCurrentStateId(orderStatus.getId());
    			    oprate += orderMapper.updateByPrimaryKeySelective(updateStateId);
    			    productMapper.downProductRepertoryNum(order.getProductId());
    			    
    				message = this.iMessage.buildSuccessMessage();
    				message.setData(oprate);
    				
    			}else {
    				throw new BaseException("当前账户积分不足支付订单，缺少"+(transactionPrice-customerIntegralSum)+"积分");
    			}
			}
			
		}else {
			throw new BaseException("当前账户为0积分");
		}
	  return message;
	}

	@Override
	public Message selectOrders(Order order) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Message selectClientOrders(Order order) throws Exception {
		  Message message = this.iMessage.buildDefaultMessage();
		  order.setCreateBy(CurrentWxMiniCustomer.obtainCustomerID());
		  //启用分页
		  if(order.isUsePagenation()) {
			  boolean usePage = false;
			  if((order.getPageNumber()==null || (order != null && order.getPageNumber()==0))
					  || (order.getPageSize()==null || (order != null && order.getPageSize()==0))
					  ){
				  logger.info("因前端程序未传参，默认只能查前10条数据");
				  PageHelper.startPage(1,10);
			  }else {
				  usePage=true;
				  PageHelper.startPage(order.getPageNumber(), order.getPageSize());
			  }
		      List<Order> orders = orderMapper.selectClientOrders(order);
		      message = this.iMessage.buildSuccessMessage();
		      PageInfo<Order> pageInfo=new PageInfo<Order>(orders);
		      if(!usePage){
		    	  message.setRealMessage("因前端程序未传参，默认只能查前10条数据");
		      }
		      message.setData(pageInfo);
		  }else {
			  //不启用分页
			  List<Order> orders = orderMapper.selectClientOrders(order);
			  message = this.iMessage.buildSuccessMessage();
			  message.setData(orders);
		  }
		return message;
	}

	@Override
	public Message deleteOrders(Order order) throws Exception {
		Message	message = this.iMessage.buildDefaultMessage();
		if(StringUtils.isEmpty(order.getIds())) {
			throw new BaseException("ids数组为空");
		}
		
		String[] idArr = order.getIds().split(",");
		int oprate = 0;
		for(String id:idArr) {
			
			Order orderDatabase = orderMapper.selectByPrimaryKey(Long.valueOf(id));
			
			OrderDeleteBackup orderDeleteBackup = new OrderDeleteBackup(orderDatabase);
			orderDeleteBackup.buildCreateDefaultValue();
			orderDeleteBackup.setCreateBy(CurrentWxMiniCustomer.obtainCustomerID());
			
			orderDeleteBackupMapper.insertSelective(orderDeleteBackup);
			
			OrderStatus orderStatus = new OrderStatus();
			orderStatus.setOrderId(Long.valueOf(id));
			
			List<OrderStatus> orderStatusHistorys = orderStatusMapper.selectOrderStatus(orderStatus);
			
			orderStatusHistorys.forEach(new Consumer<OrderStatus>() {
				@Override
				public void accept(OrderStatus t) {
					
					OrderStatusDeleteBackup orderStatusDeleteBackup = new OrderStatusDeleteBackup(t);
					orderStatusDeleteBackup.setOrderId(orderDeleteBackup.getId());
					orderStatusDeleteBackupMapper.insertSelective(orderStatusDeleteBackup);
					
					orderStatusMapper.deleteByPrimaryKey(t.getId());
				}
				
			});
			OrderStatusDeleteBackup orderStatusDeleteBackup = new OrderStatusDeleteBackup();
			orderStatusDeleteBackup.setOrderId(orderDeleteBackup.getId());
			orderStatusDeleteBackup.setStatus(4);
			orderStatusDeleteBackupMapper.insertSelective(orderStatusDeleteBackup);
			
			OrderDeleteBackup orderDeleteBackupUpdate = new OrderDeleteBackup(); 
			orderDeleteBackupUpdate.setId(orderDeleteBackup.getId());
			orderDeleteBackupUpdate.setCurrentStateId(orderStatusDeleteBackup.getId());
			orderDeleteBackupMapper.updateByPrimaryKeySelective(orderDeleteBackupUpdate);
			
			Logistics logistics = new Logistics();
			logistics.setOrderId(Long.valueOf(id));
			List<Logistics> logisticses = logisticsMapper.selectLogisticses(logistics);
			
			logisticses.forEach(new Consumer<Logistics>() {
				@Override
				public void accept(Logistics logistics) {
					
					LogisticsDeleteBackup logisticsDeleteBackup = new LogisticsDeleteBackup(logistics);
					logisticsDeleteBackup.setOrderId(orderDeleteBackup.getId());
					logisticsDeleteBackup.buildCreateDefaultValue();
					logisticsDeleteBackupMapper.insertSelective(logisticsDeleteBackup);
					
					logisticsMapper.deleteByPrimaryKey(logistics.getId());
				}
			});
			
			oprate += orderMapper.deleteByPrimaryKey(Long.valueOf(id));
		}
		message = this.iMessage.buildSuccessMessage();
		message.setData(oprate);
		return message;
	}

	@Override
	public Message updateStutus(OrderStatus orderStatus) throws Exception {
		Message message = this.iMessage.buildDefaultMessage();
		
		if(!StringUtils.isEmpty(orderStatus.getOrderId())) {
			OrderStatus query = new OrderStatus();
			query.setOrderId(orderStatus.getOrderId());
			List<OrderStatus>  OrderStatuses = orderStatusMapper.selectOrderStatus(query);
			
			if(null!=OrderStatuses && OrderStatuses.size()!=0){
				OrderStatus orderStatusTemp = OrderStatuses.get(0);
				Integer client = orderStatus.getStatus().intValue();
				Integer database =  orderStatusTemp.getStatus().intValue();
				if(client==database || client<=database) {
					throw new BaseException("当前状态操作无效，现订单状态是["+database+"]");
				}
			}
			OrderStatus setValid2 = new OrderStatus();
			setValid2.setOrderId(orderStatus.getOrderId());
			setValid2.setValid(2);
			orderStatusMapper.setValid2(setValid2);
			
			orderStatus.buildCreateDefaultValue();
			orderStatus.setCreateBy(CurrentWxMiniCustomer.obtainCustomerID());
			int oprate = orderStatusMapper.insertSelective(orderStatus);
			message = this.iMessage.buildSuccessMessage();
			message.setData(oprate);
			
		}else {
			throw new BaseException("订单参数ID空");
		}
		
		return message;
	}

}
