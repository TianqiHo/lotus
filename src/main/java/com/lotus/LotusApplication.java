package com.lotus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Import;

import com.lotus.core.dynamicDataSource.DynamicDataSourceRegister;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@Import(DynamicDataSourceRegister.class)//动态数据源
@MapperScan(basePackages = {"com.lotus.**.mapper"})
@ServletComponentScan
public class LotusApplication{

	public static void main(String[] args) {	
		SpringApplication.run(LotusApplication.class, args);
	}

}
