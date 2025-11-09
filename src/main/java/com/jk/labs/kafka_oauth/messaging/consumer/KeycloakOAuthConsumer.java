package com.jk.labs.kafka_oauth.messaging.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@ConditionalOnProperty(name = "feature.toggles.kafka.consumers.keycloak-oauth.enabled", havingValue = "true")
@Component
@Slf4j
public class KeycloakOAuthConsumer {

    @KafkaListener(topics = "oauth.keycloak.events", groupId = "oauth-keycloak-group")
    public void consume(ConsumerRecord<String, String> record) {
        log.info("Received event from Keycloak: {}", record.value());
    }
}
