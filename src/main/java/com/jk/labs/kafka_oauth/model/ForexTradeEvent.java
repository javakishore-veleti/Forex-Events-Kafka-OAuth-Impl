package com.jk.labs.kafka_oauth.model;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class ForexTradeEvent {

    @Id
    @Column(name = "trade_id", nullable = false)
    private String id;

    @Column(name = "from_currency")
    private String fromCurrency;

    @Column(name = "to_currency")
    private String toCurrency;

    @Column(name = "unit_price")
    private double unitPrice;

    @Column(name = "quantity")
    private double quantity;

    @Column(name = "total_discount")
    private double totalDiscount;

    @Column(name = "total_cost")
    private double totalCost;

    @Column(name = "total_amount")
    private double totalAmount;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;
}
