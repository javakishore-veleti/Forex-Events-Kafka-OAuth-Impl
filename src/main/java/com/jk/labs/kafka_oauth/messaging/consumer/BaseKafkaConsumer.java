package com.jk.labs.kafka_oauth.messaging.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;

public abstract class BaseKafkaConsumer {

    @SuppressWarnings("unused")
    public abstract String getTopicName();

    @KafkaListener(topics = "#{__listener.getTopicName()}", groupId = "forex-group")
    public void listen(@Payload String message, Acknowledgment ack) {
        processMessage(message);
        ack.acknowledge();
    }

    protected abstract void processMessage(String message);
}