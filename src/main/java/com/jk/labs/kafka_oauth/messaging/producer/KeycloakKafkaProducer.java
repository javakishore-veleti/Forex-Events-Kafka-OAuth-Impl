package com.jk.labs.kafka_oauth.messaging.producer;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnExpression("${feature.toggles.kafka.oauth_provider.keycloak.enabled:true} && ${feature.toggles.kafka.producers.oauth_provider.keycloak.enabled:true}")
public class KeycloakKafkaProducer extends BaseKafkaProducer {

    @Override
    protected String getTopicName() {
        return "forex-keycloak-topic";
    }
}

