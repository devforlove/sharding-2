package com.example.sharding_2;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DataSourceRouter extends AbstractRoutingDataSource {

//	private Map<Integer, MhaDataSource> shards;

	@Override
	protected Object determineCurrentLookupKey() {
		return null;
	}

//	private int getShardNo() {
//
//	}
}
