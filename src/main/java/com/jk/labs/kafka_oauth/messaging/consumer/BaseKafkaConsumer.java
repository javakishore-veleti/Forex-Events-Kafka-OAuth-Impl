package com.jk.labs.kafka_oauth.messaging.consumer;

import com.jk.labs.kafka_oauth.dto.TradeEventMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;

@Slf4j
public abstract class BaseKafkaConsumer {

    public BaseKafkaConsumer() {
        log.info("Initializing BaseKafkaConsumer for topic: " + getTopicName());
    }

    @SuppressWarnings("unused")
    public abstract String getTopicName();

    @KafkaListener(
            topics = "#{__listener.getTopicName()}",
            groupId = "forex-group",
            containerFactory = "#{__listener.getListenerFactoryName()}"
    )
    public void listen(@Payload TradeEventMessage tradeEventMessage, Acknowledgment ack) {
        processMessage(tradeEventMessage);
        ack.acknowledge();
    }

    protected abstract void processMessage(TradeEventMessage message);

    // Each subclass will tell which listener container factory it needs
    public String getListenerFactoryName() {
        String className = getClass().getSimpleName().toLowerCase();
        if (className.contains("keycloak")) return "keycloakKafkaListenerContainerFactory";
        if (className.contains("google")) return "googleKafkaListenerContainerFactory";
        if (className.contains("github")) return "githubKafkaListenerContainerFactory";
        if (className.contains("microsoft")) return "microsoftKafkaListenerContainerFactory";
        return "keycloakKafkaListenerContainerFactory";
    }
}