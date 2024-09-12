package com.example.sharding_2;

import static com.example.sharding_2.ShardConstants.*;

import com.example.sharding_2.FriendShardingDataSourceProperty.Property;
import com.example.sharding_2.FriendShardingDataSourceProperty.Shard;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

@Configuration
@EnableConfigurationProperties(FriendShardingDataSourceProperty.class)
public class FriendConfig {

	private final FriendShardingDataSourceProperty property;

	public FriendConfig(FriendShardingDataSourceProperty property) {
		this.property = property;
	}

	@Bean
	public DataSource friendDataSource() {
		DataSourceRouter router = new DataSourceRouter();
		Map<Object, Object> dataSourceMap = new LinkedHashMap<>();

		for (int i = 0; i < property.getShards().size(); i++) {
			Shard shard = property.getShards().get(i);

			DataSource masterDataSource = dataSource(shard.getUsername(), shard.getPassword(), shard.getMaster().getUrl());
			dataSourceMap.put(i + SHARD_DELIMITER + shard.getMaster().getName(), masterDataSource);

			for (Property slave : shard.getSlaves()) {
				DataSource slaveDataSource = dataSource(shard.getUsername(), shard.getPassword(), slave.getUrl());
				dataSourceMap.put(i + SHARD_DELIMITER + slave.getName(), slaveDataSource);
			}
		}

		router.setTargetDataSources(dataSourceMap);
		router.afterPropertiesSet();
		return new LazyConnectionDataSourceProxy(router);
	}

	private DataSource dataSource(String username, String password, String url) {
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl(url);
		config.setUsername(username);
		config.setPassword(password);

		return new HikariDataSource(config);
	}
}
