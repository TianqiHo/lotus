package com.lotus.core.dynamicDataSource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.lotus.core.base.baselogger.AbstractLogger;
import com.lotus.core.dynamicDataSource.annotation.TargetDataSource;


/**
 * @Description 动态数据源通知
 */
@Aspect
@Order(-1)//保证在@Transactional之前执行
@Component
public class DynamicDataSourceAspect extends AbstractLogger{

	@Override
	protected Class<?> getClassForLogger() {
		return DynamicDataSourceAspect.class;
	}
	
	
	//改变数据源
    @Before("@annotation(targetDataSource)")
    public void changeDataSource(JoinPoint joinPoint, TargetDataSource targetDataSource) {
        String dbid = targetDataSource.name();

        if (!DynamicDataSourceContextHolder.isDatasourceHere(dbid)) {
            //joinPoint.getSignature() ：获取连接点的方法签名对象
            logger.error("数据源 " + dbid + " 不存在使用默认的数据源 -> " + joinPoint.getSignature());
        } else {
            logger.debug("使用数据源：" + dbid);
            DynamicDataSourceContextHolder.setDatasourceToContext(dbid);
        }
    }

    @After("@annotation(targetDataSource)")
    public void clearDataSource(JoinPoint joinPoint, TargetDataSource targetDataSource) {
        logger.debug("清除数据源 " + targetDataSource.name() + " !");
        DynamicDataSourceContextHolder.getCurrentDataSourceFromContext();
    }

}
