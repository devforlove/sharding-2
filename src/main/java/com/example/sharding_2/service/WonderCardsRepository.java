package com.example.sharding_2.service;

import com.example.sharding_2.entity.WonderCardsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WonderCardsRepository extends JpaRepository<WonderCardsEntity, Long> {

}
