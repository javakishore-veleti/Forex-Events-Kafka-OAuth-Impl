package com.kv.labs.kafka_oauth.messaging.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ConditionalOnExpression("${feature.toggles.kafka.oauth_provider.google.enabled:false} && ${feature.toggles.kafka.consumers.oauth_provider.google.enabled:false}")
public class GoogleEventConsumer {

    @KafkaListener(
            topics = "oauth.google.events",
            containerFactory = "googleKafkaListenerFactory"
    )
    public void listen(String message) {
        log.info("Received Google event: " + message);
    }
}
