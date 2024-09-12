package com.example.sharding_2;

import static com.example.sharding_2.ShardConstants.SHARD_DELIMITER;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DataSourceRouter extends AbstractRoutingDataSource {

	private final Map<Integer, MhaDataSource> shards;

	public DataSourceRouter() {
		this.shards = new HashMap<>();
	}

	@Override
	public void setTargetDataSources(Map<Object, Object> targetDataSources) {
		super.setTargetDataSources(targetDataSources);

		for (Object item : targetDataSources.keySet()) {
			String dataSourceName = item.toString();
			String shardNoStr = dataSourceName.split(SHARD_DELIMITER)[0];
		}

	}

	@Override
	protected Object determineCurrentLookupKey() {
		return null;
	}

	private int getShardNo(UserHolder.Sharding sharding) {
		if (sharding == null) {
			return 0;
		}

		int shardNo = 0;
		return 0;
	}

	@Getter
	@Setter
	private class MhaDataSource {
		private String masterName;
//		private RoundRobin<String> slaveName;
	}
}
