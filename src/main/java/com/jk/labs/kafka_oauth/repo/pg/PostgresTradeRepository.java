package com.jk.labs.kafka_oauth.repo.pg;

import com.jk.labs.kafka_oauth.model.pg.PostgresTradeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostgresTradeRepository extends JpaRepository<PostgresTradeEntity, Long> { }
