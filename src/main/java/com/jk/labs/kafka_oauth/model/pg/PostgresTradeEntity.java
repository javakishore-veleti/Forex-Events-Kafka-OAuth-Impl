package com.jk.labs.kafka_oauth.model.pg;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "forex_trades")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostgresTradeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String provider;
    private String fromCurrency;
    private String toCurrency;
    private double unitPrice;
    private double quantity;
    private double totalAmount;
}
