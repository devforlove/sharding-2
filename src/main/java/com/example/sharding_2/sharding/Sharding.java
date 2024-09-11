package com.example.sharding_2.sharding;

import com.example.sharding_2.ShardingTarget;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Sharding {
	ShardingTarget target();
}
