package com.example.sharding_2;

import javax.sql.DataSource;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

@Configuration
@EnableConfigurationProperties(ShardingDataSourceProperty.class)
public class FriendConfig {

	private final ShardingDataSourceProperty property;

	public FriendConfig(ShardingDataSourceProperty property) {
		this.property = property;
	}

	@Bean
	public DataSource friendDataSource() {



		return new LazyConnectionDataSourceProxy();
	}
}
