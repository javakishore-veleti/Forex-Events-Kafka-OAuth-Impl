package com.jk.labs.kafka_oauth.messaging.consumer;

import com.jk.labs.kafka_oauth.config.KafkaClusterConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;

public abstract class BaseKafkaConsumer {

    @Autowired
    private KafkaClusterConfig kafkaClusterConfig;

    @SuppressWarnings("unused")
    public abstract String getTopicName();

    @KafkaListener(topics = "#{__listener.getTopicName()}", groupId = "forex-group")
    public void listen(@Payload String message, Acknowledgment ack) {
        processMessage(message);
        ack.acknowledge();
    }

    public void configureKafkaTemplate(String provider) {
        String bootstrapServer = kafkaClusterConfig.getBootstrap(provider);
        // Use this when building KafkaTemplate or ConsumerFactory
    }

    protected abstract void processMessage(String message);
}