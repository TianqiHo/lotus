package com.lotus.core.dynamicDataSource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		
		return DynamicDataSourceContextHolder.getCurrentDataSourceFromContext();
	}

}
