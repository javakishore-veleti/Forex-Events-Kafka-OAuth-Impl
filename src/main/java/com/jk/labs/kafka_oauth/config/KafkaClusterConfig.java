package com.jk.labs.kafka_oauth.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "forex.kafka")
public class KafkaClusterConfig {

    private Map<String, String> brokers = new HashMap<>();
    private Map<String, Map<String, String>> oauth;

    public String getBootstrap(String provider) {
        return brokers.getOrDefault(provider, "localhost:9092");
    }

    public Map<String, String> getOauthConfig(String provider) {
        return oauth.get(provider);
    }
}
