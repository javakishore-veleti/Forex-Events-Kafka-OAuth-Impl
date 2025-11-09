package com.jk.labs.kafka_oauth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "forex.kafka")
public class KafkaClusterConfig {

    private Map<String, String> brokers = new HashMap<>();

    public Map<String, String> getBrokers() {
        return brokers;
    }

    public void setBrokers(Map<String, String> brokers) {
        this.brokers = brokers;
    }

    public String getBootstrap(String provider) {
        return brokers.getOrDefault(provider, "localhost:9092");
    }
}
