package com.example.sharding_2;

import static com.example.sharding_2.ShardConstants.MASTER;
import static com.example.sharding_2.ShardConstants.SHARD_DELIMITER;
import static com.example.sharding_2.ShardConstants.SLAVE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class DataSourceRouter extends AbstractRoutingDataSource {

	private final Map<Integer, MhaDataSource> shards =  new HashMap<>();;

	@Override
	public void setTargetDataSources(Map<Object, Object> targetDataSources) {
		super.setTargetDataSources(targetDataSources);

		for (Object item : targetDataSources.keySet()) {
			String dataSourceName = item.toString();
			String shardNoStr = dataSourceName.split(SHARD_DELIMITER)[0];

			MhaDataSource shard = getShard(shardNoStr);
			if (dataSourceName.contains(MASTER)) {
				shard.setMasterName(dataSourceName);
			} else if (dataSourceName.contains(SLAVE)) {
				shard.getSlaveName().add(dataSourceName);
			}
		}
	}

	@Override
	protected Object determineCurrentLookupKey() {
		int shardNo = getShardNo(UserHolder.getSharding());
		MhaDataSource dataSource = shards.get(shardNo);
		return TransactionSynchronizationManager.isCurrentTransactionReadOnly() ? dataSource.getSlaveName().next() : dataSource.getMasterName();
	}

	private int getShardNo(UserHolder.Sharding sharding) {
		if (sharding == null) {
			return 0;
		}

		return getShardNoByModular(2, sharding.getShardKey());
	}

	private int getShardNoByModular(int modules, long shardKey) {
		return (int) (shardKey % modules);
	}

	private MhaDataSource getShard(String shardNoStr) {
		final int shardNo = Integer.parseInt(shardNoStr);
		MhaDataSource shard = shards.get(shardNo);
		if (shard == null) {
			shard = new MhaDataSource();
			shard.setSlaveName(new RoundRobin<>(new ArrayList<>()));
			shards.put(shardNo, shard);
		}

		return shard;
	}

	@Getter
	@Setter
	private static class MhaDataSource {
		private String masterName;
		private RoundRobin<String> slaveName;
	}

	private static class RoundRobin<T> {
		private final List<T> list;
		private final Iterator<T> iterator;
		private int index;

		public RoundRobin(List<T> list) {
			this.list = list;
			index = 0;
			this.iterator = new Iterator<T>() {
				@Override
				public boolean hasNext() {
					return true;
				}

				@Override
				public T next() {
					T value = list.get(index);
					index = (index + 1) % list.size();

					return value;
				}
			};
		}

		public T next() {
			return iterator.next();
		}

		public void add(T item) {
			list.add(item);
		}
	}
}
