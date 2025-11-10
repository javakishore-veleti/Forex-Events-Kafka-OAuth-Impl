package com.kv.labs.kafka_oauth.messaging.producer;

import com.kv.labs.kafka_oauth.dto.TradeEventMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@ConditionalOnExpression("${feature.toggles.kafka.oauth_provider.microsoft.enabled:false} && ${feature.toggles.kafka.producers.oauth_provider.microsoft.enabled:false}")
@Component
public class MicrosoftEventPublisher extends BaseKafkaProducer {

    public MicrosoftEventPublisher(@Qualifier("microsoftKafkaTemplate") KafkaTemplate<String, TradeEventMessage> kafkaTemplate) {
        super(kafkaTemplate);
    }

    @SuppressWarnings("unused")
    public void publish(String topic, TradeEventMessage message) {
        send(topic, message);
    }
}
