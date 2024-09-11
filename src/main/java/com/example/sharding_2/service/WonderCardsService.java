package com.example.sharding_2.service;

import com.example.sharding_2.ShardingTarget;
import com.example.sharding_2.entity.WonderCardsEntity;
import com.example.sharding_2.sharding.Sharding;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Sharding(target = ShardingTarget.WONDER_CARDS)
@RequiredArgsConstructor
public class WonderCardsService {

	private final WonderCardsRepository repository;

	@Transactional
	public void save(long userId, WonderCardsEntity entity) {
		repository.save(entity);
	}
}
