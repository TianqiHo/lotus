package com.lotus.core.dynamicDataSource;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

import com.lotus.core.base.baselogger.AbstractLogger;


/**
 * 实例并注册数据源bean
 * @author Tianqi.He
 *
 */
public class DynamicDataSourceRegister extends AbstractLogger implements ImportBeanDefinitionRegistrar,EnvironmentAware{

	@Override
	protected Class<?> getClassForLogger() {
		return DynamicDataSourceRegister.class;
	}

	private static final String datasourcePoolType = "com.zaxxer.hikari.HikariDataSource";
	
	private DataSource defaultDatasource;
	
	private Map<String, DataSource> otherDataSources = new HashMap<String, DataSource>();
	
	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		Map<Object, Object> targetDataSources = new HashMap<Object, Object>(){
			{
				put("dataSource", defaultDatasource);
			}
		};
		
	  DynamicDataSourceContextHolder.dataSourceIds.add("dataSource");
	  
	  targetDataSources.putAll(otherDataSources);
	  
	  for(String key : otherDataSources.keySet()) {
		  DynamicDataSourceContextHolder.dataSourceIds.add(key);
	  }
	  
	  GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
	  beanDefinition.setBeanClass(DynamicDataSource.class);
	  beanDefinition.setSynthetic(true);
	  
	  MutablePropertyValues mpv = beanDefinition.getPropertyValues();
	  mpv.addPropertyValue("defaultTargetDataSource",defaultDatasource);
	  mpv.addPropertyValue("targetDataSources", targetDataSources);
	  
	  registry.registerBeanDefinition("dataSource", beanDefinition);
	  
	  logger.info("----------Dynamic DataSource Registry----------");
	}

	@Override
	public void setEnvironment(Environment environment) {
		 initDefaultDataSource(environment);
	     initOtherDataSources(environment);
	}

    private void initDefaultDataSource(Environment env) {
        // 读取主数据源
        Map<String, Object> dsMap = new HashMap<>();
        dsMap.put("driver", env.getProperty("spring.datasource.driver"));
        dsMap.put("url", env.getProperty("spring.datasource.url"));
        dsMap.put("username", env.getProperty("spring.datasource.username"));
        dsMap.put("password", env.getProperty("spring.datasource.password"));
        defaultDatasource = buildDataSource(dsMap);
    }
  
   private void initOtherDataSources(Environment env) {
    // 读取配置文件获取更多数据源
    String dsPrefixs = env.getProperty("other.datasource.names");
    int i = 0;
    for (String dsPrefix : dsPrefixs.split(",")) {
        // 多个数据源
        Map<String, Object> dsMap = new HashMap<>();
        dsMap.put("driver", env.getProperty("other.datasources["+i+"]." + dsPrefix + ".driver"));
        dsMap.put("url", env.getProperty("other.datasources["+i+"]." + dsPrefix + ".url"));
        dsMap.put("username", env.getProperty("other.datasources["+i+"]." + dsPrefix + ".username"));
        dsMap.put("password", env.getProperty("other.datasources["+i+"]." + dsPrefix + ".password"));
        DataSource ds = buildDataSource(dsMap);
        otherDataSources.put(dsPrefix, ds);
        i++;
    }
   }
	 
	 public DataSource buildDataSource(Map<String, Object> dataSourceMap) {
	        try {
	            Object datasourcePoolType = dataSourceMap.get("type");
	            if (datasourcePoolType == null) {
	            	datasourcePoolType = this.datasourcePoolType;// 默认DataSource
	            }
	            Class<? extends DataSource> datasourcePoolTypeInstance;
	            datasourcePoolTypeInstance = (Class<? extends DataSource>) Class.forName((String) datasourcePoolType);
	            String driverClassName = dataSourceMap.get("driver").toString();
	            String url = dataSourceMap.get("url").toString();
	            String username = dataSourceMap.get("username").toString();
	            String password = dataSourceMap.get("password").toString();
	            // 自定义DataSource配置
	            DataSourceBuilder factory = DataSourceBuilder.create().driverClassName(driverClassName).url(url).username(username).password(password).type(datasourcePoolTypeInstance);
	            return factory.build();
	        } catch (ClassNotFoundException e) {
	            e.printStackTrace();
	        }
	        return null;
	  }
}
