package com.jk.labs.kafka_oauth.model.h2;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Table(name = "forex_trades")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class H2TradeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String provider;
    private String fromCurrency;
    private String toCurrency;
    private double unitPrice;
    private double quantity;
    private double totalAmount;
}

