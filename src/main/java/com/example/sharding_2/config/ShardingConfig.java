package com.example.sharding_2.config;

import com.example.sharding_2.ShardingProperty;
import com.example.sharding_2.ShardingTarget;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Setter;

@Setter
public class ShardingConfig {
	private static Map<ShardingTarget, ShardingProperty> shardingPropertyMap = new ConcurrentHashMap<>();

	public static Map<ShardingTarget, ShardingProperty> getShardingPropertyMap() {
		return shardingPropertyMap;
	}
}
