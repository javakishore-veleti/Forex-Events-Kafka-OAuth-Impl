package com.jk.labs.kafka_oauth.dto;

import lombok.Data;

@Data
public class TradeEventMessage {
    private Long id;

    private String provider;
    private String fromCurrency;
    private String toCurrency;
    private double unitPrice;
    private double quantity;
    private double totalAmount;
}
