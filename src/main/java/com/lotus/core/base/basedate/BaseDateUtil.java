package com.lotus.core.base.basedate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseDateUtil {
	
	private static ThreadLocal<BaseDateUtil> currentBaseDate = new ThreadLocal<BaseDateUtil>();
	
	public static BaseDateUtil Instance() {
		BaseDateUtil instance = currentBaseDate.get();
		if(null==instance) {
			instance = new BaseDateUtil();
			currentBaseDate.set(instance);
		}
		return instance;
	}
	
	public String obtainymd(){
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat(Pattern.YMD.getPattern());
		return dateFormat.format(date);
	}
	
	public enum Pattern{
		YMD("yyyy/MM/dd");
		private String pattern;
		
		public String getPattern() {
			return pattern;
		}

		public void setPattern(String pattern) {
			this.pattern = pattern;
		}

		Pattern(String pattern) {
			this.pattern = pattern;
		}
	}
}
