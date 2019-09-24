package com.lotus.core.concurrent;

import com.lotus.smallroutine.customer.model.Customer;

/**
 * 微信登陆用户容器
 * @author Tianqi.He
 *
 */
public final class CurrentWxMiniCustomer {

	private CurrentWxMiniCustomer() {}
	/**
	 * 存储前端用户
	 */
	private static ThreadLocal<Customer> currentContext = null;
	
	public final static ThreadLocal<Customer> obtain(){
		if(null==currentContext) {
			currentContext = new ThreadLocal<Customer>();
		}
		return currentContext;
	}
	
    /**
     * 获取当前线程中的用户
     */
	public final static Customer obtainCustomer() {
		Customer customer = null;
		if(null!=currentContext) {
			customer = currentContext.get();
		}
		return customer;
	}
	
	 /**
     * 获取当前线程中的用户ID
     */
	public final static Long obtainCustomerID() {
		Customer customer = obtainCustomer();
		if(null!=customer) return customer.getId();
		return null;
	}
	
	 /**
     * 获取当前线程中的用户
     */
	public final static void setCustomer(Customer customer) {
		if(null!=currentContext) {
			currentContext.set(customer);
		}
	}
	
	/**
     * 获取当前线程中的用户
     */
	public final static void clear() {
		if(null!=currentContext) {
			currentContext.remove();
			currentContext = null;
		}
	}
}
