package com.lotus.core.jwt.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 *    需要登陆声明
 * @author Tianqi.He
 *
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ClientRequireLogin {
	 
	 boolean require() default true;
	 
	 String[] excludeMethodName() default {};
}
