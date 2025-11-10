package com.kv.labs.kafka_oauth.model.h2;

import com.kv.labs.kafka_oauth.model.ForexTradeEvent;
import jakarta.persistence.*;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Table(name = "forex_trades")
public class H2TradeEntity extends ForexTradeEvent {

}

