package com.kv.labs.kafka_oauth.messaging.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ConditionalOnExpression("${feature.toggles.kafka.oauth_provider.github.enabled:false} && ${feature.toggles.kafka.consumers.oauth_provider.github.enabled:false}")
public class GithubEventConsumer {

    @KafkaListener(
            topics = "oauth.github.events",
            containerFactory = "githubKafkaListenerFactory"
    )
    public void listen(String message) {
        log.info("Received Github event: " + message);
    }
}
