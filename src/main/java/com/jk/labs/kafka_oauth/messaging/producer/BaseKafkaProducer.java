package com.jk.labs.kafka_oauth.messaging.producer;


import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.beans.factory.annotation.Autowired;


public abstract class BaseKafkaProducer {

    @Autowired
    protected KafkaTemplate<String, String> kafkaTemplate;

    protected abstract String getTopicName();

    public void send(String key, String message) {
        kafkaTemplate.send(getTopicName(), key, message);
    }
}
