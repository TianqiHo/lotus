package com.lotus.core.dynamicDataSource;

import java.util.ArrayList;
import java.util.List;

import com.lotus.core.base.baselogger.AbstractLogger;


public class DynamicDataSourceContextHolder extends AbstractLogger{

	@Override
	protected Class<?> getClassForLogger() {
		return DynamicDataSourceContextHolder.class;
	}

	//当前线程数据源上下文
	private static final ThreadLocal<String> currentDatasourceContext = new ThreadLocal<>();

	//数据源IDs
	public static List<String> dataSourceIds = new ArrayList<>();

	
	
	public static void setDatasourceToContext(String whichDatasource) {
		currentDatasourceContext.set(whichDatasource);
	}
	
	public static String getCurrentDataSourceFromContext() {
		return currentDatasourceContext.get();
	}
	
	public static void clearCurrentDataSourceFromContext() {
		currentDatasourceContext.remove();
	}
	
	public static boolean isDatasourceHere(String whichDatasource) {
		return dataSourceIds.contains(whichDatasource);
	}
	
	
}
