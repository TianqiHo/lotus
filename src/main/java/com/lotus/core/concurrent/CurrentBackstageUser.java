package com.lotus.core.concurrent;

import com.lotus.backstage.user.model.User;


/**
 * 后台用户容器
 * @author Tianqi.He
 *
 */
public final class CurrentBackstageUser{

	private CurrentBackstageUser() {}
	/**
	 * 存储前端用户
	 */
	private static ThreadLocal<User> currentContext = null;
	
	public final static ThreadLocal<User> obtain(){
		if(null==currentContext) {
			currentContext = new ThreadLocal<User>();
		}
		return currentContext;
	}
	
    /**
     * 获取当前线程中的用户
     */
	public final static User obtainUser() {
		User user = null;
		if(null!=currentContext) {
			user = currentContext.get();
		}
		return user;
	}
	
	 /**
     * 获取当前线程中的用户ID
     */
	public final static Long obtainUserID() {
		User user = obtainUser();
		if(null!=user) return user.getId();
		return null;
	}
	
	 /**
     * 获取当前线程中的用户
     */
	public final static void setUser(User user) {
		if(null!=currentContext) {
			currentContext.set(user);
			
		}
	}
	
	/**
     * 获取当前线程中的用户
     */
	public final static void clear() {
		if(null!=currentContext) {
			currentContext.remove();
			currentContext=null;
		}
	}
}
