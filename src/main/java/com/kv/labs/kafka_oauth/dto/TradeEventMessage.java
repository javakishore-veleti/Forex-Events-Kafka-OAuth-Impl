package com.kv.labs.kafka_oauth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TradeEventMessage {
    private String id;

    @JsonProperty("provider")
    private String provider;

    @JsonProperty("fromCurrency")
    private String fromCurrency;

    @JsonProperty("toCurrency")
    private String toCurrency;

    @JsonProperty("unitPrice")
    private Double unitPrice;

    @JsonProperty("quantity")
    private Double quantity;

    @JsonProperty("totalAmount")
    private Double totalAmount;

    @JsonProperty("status")
    private String status;

    @JsonProperty("createdAt")
    private Date createdAt;


}
