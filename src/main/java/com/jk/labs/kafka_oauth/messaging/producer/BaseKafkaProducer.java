package com.jk.labs.kafka_oauth.messaging.producer;

import org.springframework.kafka.core.KafkaTemplate;

public abstract class BaseKafkaProducer {

    protected final KafkaTemplate<String, String> kafkaTemplate;

    protected BaseKafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    protected void send(String topic, String message) {
        kafkaTemplate.send(topic, message);
    }
}
