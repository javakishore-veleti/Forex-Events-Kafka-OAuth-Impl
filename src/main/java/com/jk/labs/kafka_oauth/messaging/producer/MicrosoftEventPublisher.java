package com.jk.labs.kafka_oauth.messaging.producer;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class MicrosoftEventPublisher extends BaseKafkaProducer {

    public MicrosoftEventPublisher(@Qualifier("microsoftKafkaTemplate") KafkaTemplate<String, String> kafkaTemplate) {
        super(kafkaTemplate);
    }

    public void publish(String topic, String message) {
        send(topic, message);
    }
}
