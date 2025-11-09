package com.jk.labs.kafka_oauth.messaging.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@SuppressWarnings("unused")
@Slf4j
@ConditionalOnExpression("${feature.toggles.kafka.oauth_provider.github.enabled:false} && ${feature.toggles.kafka.producers.oauth_provider.github.enabled:false}")
@Component
public class GithubEventPublisher extends BaseKafkaProducer {

    public GithubEventPublisher(@Qualifier("githubKafkaTemplate") KafkaTemplate<String, String> kafkaTemplate) {
        super(kafkaTemplate);
    }

    public void publish(String topic, String message) {
        send(topic, message);
    }
}
