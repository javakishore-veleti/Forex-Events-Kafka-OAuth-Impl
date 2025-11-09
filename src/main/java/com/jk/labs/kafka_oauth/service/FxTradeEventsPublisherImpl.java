package com.jk.labs.kafka_oauth.service;

import com.jk.labs.kafka_oauth.constants.AppKafkaConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Slf4j
@Service
public class FxTradeEventsPublisherImpl implements FxTradeEventsPublisher {

    @Autowired
    private ApplicationContext context;

    // caches resolved templates and topic names lazily
    private final Map<String, KafkaTemplate<String, String>> templateCache = new ConcurrentHashMap<>();
    private final Map<String, String> topicCache = new ConcurrentHashMap<>();

    // bean names per provider
    private static final Map<String, String> TEMPLATE_BEAN_NAMES = Map.of(
            "keycloak", "keycloakKafkaTemplate",
            "google", "googleKafkaTemplate",
            "github", "githubKafkaTemplate",
            "microsoft", "microsoftKafkaTemplate"
    );

    @Override
    public void publishFxTradeEvent(int noOfEvents, String authProvidersCsv, String kafkaTopicsCsv) {
        List<String> providers = ObjectUtils.isEmpty(authProvidersCsv)
                ? AppKafkaConstants.AUTH_PROVIDERS
                : List.of(authProvidersCsv.trim().toLowerCase(Locale.ROOT).split(","));

        List<String> topics = ObjectUtils.isEmpty(kafkaTopicsCsv)
                ? AppKafkaConstants.KAFKA_TOPIC_NAMES
                : List.of(kafkaTopicsCsv.trim().toLowerCase(Locale.ROOT).split(","));

        log.info("Publishing {} events for providers: {}", noOfEvents, providers);

        for (int i = 1; i <= noOfEvents; i++) {
            log.info("🚀 START FX Trade Event #{}", i);

            for (String provider : providers) {
                KafkaTemplate<String, String> template = getKafkaTemplate(provider);
                String topic = getTopicName(provider, topics);

                if (template == null) {
                    log.warn("No KafkaTemplate found for provider '{}'. Skipping.", provider);
                    continue;
                }

                try {
                    String message = String.format("FX Trade Event #%d for %s", i, provider);
                    template.send(topic, message);
                    log.info("Published to {} → {}", provider, topic);
                } catch (Exception ex) {
                    log.error("Error publishing for '{}': {}", provider, ex.getMessage(), ex);
                }
            }

            log.info("COMPLETED FX Trade Event #{}", i);
        }
    }

    /**
     * Lazily resolves and caches KafkaTemplate.
     */
    @SuppressWarnings("unchecked")
    private KafkaTemplate<String, String> getKafkaTemplate(String provider) {
        return templateCache.computeIfAbsent(provider, p -> {
            String beanName = TEMPLATE_BEAN_NAMES.get(p);
            if (beanName == null) {
                log.warn("No bean mapping found for provider '{}'", p);
                return null;
            }

            if (!context.containsBean(beanName)) {
                log.warn("KafkaTemplate bean '{}' not found in context (toggle may be off)", beanName);
                return null;
            }

            try {
                KafkaTemplate<String, String> template = context.getBean(beanName, KafkaTemplate.class);
                log.info("Cached KafkaTemplate for '{}'", p);
                return template;
            } catch (Exception ex) {
                log.error("Failed to get KafkaTemplate for '{}': {}", p, ex.getMessage());
                return null;
            }
        });
    }

    /**
     * Lazily resolves and caches topic names.
     */
    private String getTopicName(String provider, List<String> inputTopics) {
        return topicCache.computeIfAbsent(provider, p -> {
            for (String t : inputTopics) {
                if (t.contains(p)) {
                    log.info("Cached topic '{}' for provider '{}'", t, p);
                    return t;
                }
            }
            String defaultTopic = AppKafkaConstants.KAFKA_TOPIC_MAP.getOrDefault(p, "forex-" + p + "-topic");
            log.info("Cached default topic '{}' for provider '{}'", defaultTopic, p);
            return defaultTopic;
        });
    }
}
