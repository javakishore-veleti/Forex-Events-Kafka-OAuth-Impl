package com.jk.labs.kafka_oauth.messaging.producer;

import com.jk.labs.kafka_oauth.dto.TradeEventMessage;
import org.springframework.kafka.core.KafkaTemplate;

public abstract class BaseKafkaProducer {

    protected final KafkaTemplate<String, TradeEventMessage> kafkaTemplate;

    protected BaseKafkaProducer(KafkaTemplate<String, TradeEventMessage> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    protected void send(String topic, TradeEventMessage message) {
        kafkaTemplate.send(topic, message);
    }
}
