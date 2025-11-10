package com.kv.labs.kafka_oauth.model.pg;

import com.kv.labs.kafka_oauth.model.ForexTradeEvent;
import jakarta.persistence.*;

@Entity
@Table(name = "forex_trades")
public class PostgresTradeEntity extends ForexTradeEvent {

}
