package com.example.sharding_2;

import lombok.Getter;
import lombok.Setter;

public class UserHolder {

	private static final ThreadLocal<Context> userContext = new ThreadLocal<>();

	public static void setSharding(ShardingTarget target, long shardKey) {
		getUserContext().setSharding(new Sharding(target, shardKey));
	}

	public static Sharding getSharding() {
		return getUserContext().getSharding();
	}

	private static Context getUserContext() {
		return userContext.get();
	}

	@Getter
	@Setter
	public static class Context {
		private Sharding sharding;
	}

	@Getter
	@Setter
	public static class Sharding {
		private ShardingTarget target;
		private long shardKey;

		public Sharding(ShardingTarget target, long shardKey) {
			this.target = target;
			this.shardKey = shardKey;
		}
	}
}
