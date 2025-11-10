package com.kv.labs.kafka_oauth.messaging.consumer;

import com.kv.labs.kafka_oauth.dto.TradeEventMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ConditionalOnExpression("${feature.toggles.kafka.oauth_provider.kafkaDefault.enabled:false} && ${feature.toggles.kafka.consumers.oauth_provider.kafkaDefault.enabled:false}")
public class KafkaDefaultEventConsumer extends BaseKafkaConsumer {

    @Override
    public String getTopicName() {
        return "forex-kafkaDefault-topic";
    }

    @Override
    protected void processMessage(TradeEventMessage message) {
        log.info("kafkaDefault Consumer received: " + message);
    }
}
