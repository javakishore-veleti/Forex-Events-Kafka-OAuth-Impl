package com.jk.labs.kafka_oauth.repo.h2;

import com.jk.labs.kafka_oauth.model.h2.H2TradeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface H2TradeRepository extends JpaRepository<H2TradeEntity, Long> {
}
