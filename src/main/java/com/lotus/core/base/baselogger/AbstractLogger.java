package com.lotus.core.base.baselogger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

abstract public class AbstractLogger {

	/**
	 * 获取待打印日志的类字节码
	 * @return
	 */
    protected abstract Class<?> getClassForLogger();
	
	protected final Logger logger = LoggerFactory.getLogger(getClassForLogger());
}
