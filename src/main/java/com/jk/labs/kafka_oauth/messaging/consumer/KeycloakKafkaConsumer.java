package com.jk.labs.kafka_oauth.messaging.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ConditionalOnExpression("${feature.toggles.kafka.oauth_provider.keycloak.enabled:false} && ${feature.toggles.kafka.consumers.oauth_provider.keycloak.enabled:false}")
public class KeycloakKafkaConsumer extends BaseKafkaConsumer {

    @Override
    public String getTopicName() {
        return "forex-keycloak-topic";
    }

    @Override
    protected void processMessage(String message) {
        log.info("Keycloak Consumer received: " + message);
    }
}
