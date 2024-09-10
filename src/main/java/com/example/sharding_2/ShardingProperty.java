package com.example.sharding_2;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShardingProperty {
	private ShardingStrategy strategy;
	private List<ShardingRule> rules;
	private int mod;

	@Getter
	@Setter
	public static class ShardingRule {
		private int shardNo;
		private long rangeMin;
		private long rangeMax;
	}
}
