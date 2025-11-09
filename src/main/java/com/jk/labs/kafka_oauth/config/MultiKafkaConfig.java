package com.jk.labs.kafka_oauth.config;

import com.jk.labs.kafka_oauth.dto.TradeEventMessage;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
public class MultiKafkaConfig {

    @Autowired
    private KafkaClusterConfig kafkaClusterConfig;

    /**
     * Common producer properties shared across all providers.
     */
    private Map<String, Object> baseProducerProps() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        // Use JsonSerializer for sending POJOs as JSON
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false); // cleaner JSON
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.RETRIES_CONFIG, 3);
        props.put(ProducerConfig.LINGER_MS_CONFIG, 5);
        return props;
    }

    /**
     * Builds a producer factory for the given OAuth provider.
     */
    private ProducerFactory<String, TradeEventMessage> producerFactoryFor(String provider) {
        Map<String, Object> props = new HashMap<>(baseProducerProps());
        String bootstrap = kafkaClusterConfig.getBootstrap(provider);

        if (bootstrap == null || bootstrap.isBlank()) {
            log.warn("No Kafka bootstrap found for provider '{}'. Falling back to localhost:9093", provider);
            bootstrap = "localhost:9093";
        }

        //props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9093");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class); // Jackson serializer

        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrap);
        log.info("Kafka ProducerFactory created for '{}' → bootstrap.servers={}", provider, bootstrap);

        return new DefaultKafkaProducerFactory<>(props);
    }

    // ---------------------------------------------------------------------
    // Provider-specific ProducerFactories & KafkaTemplates
    // ---------------------------------------------------------------------

    @Bean(name = "keycloakProducerFactory")
    @ConditionalOnProperty(value = "feature.toggles.kafka.producers.oauth_provider.keycloak.enabled", havingValue = "true")
    public ProducerFactory<String, TradeEventMessage> keycloakProducerFactory() {
        return producerFactoryFor("keycloak");
    }

    @Bean(name = "keycloakKafkaTemplate")
    @ConditionalOnProperty(value = "feature.toggles.kafka.producers.oauth_provider.keycloak.enabled", havingValue = "true")
    public KafkaTemplate<String, TradeEventMessage> keycloakKafkaTemplate() {
        return new KafkaTemplate<>(keycloakProducerFactory());
    }

    @Bean(name = "googleProducerFactory")
    @ConditionalOnProperty(value = "feature.toggles.kafka.producers.oauth_provider.google.enabled", havingValue = "true")
    public ProducerFactory<String, TradeEventMessage> googleProducerFactory() {
        return producerFactoryFor("google");
    }

    @Bean(name = "googleKafkaTemplate")
    @ConditionalOnProperty(value = "feature.toggles.kafka.producers.oauth_provider.google.enabled", havingValue = "true")
    public KafkaTemplate<String, TradeEventMessage> googleKafkaTemplate() {
        return new KafkaTemplate<>(googleProducerFactory());
    }

    @Bean(name = "githubProducerFactory")
    @ConditionalOnProperty(value = "feature.toggles.kafka.producers.oauth_provider.github.enabled", havingValue = "true")
    public ProducerFactory<String, TradeEventMessage> githubProducerFactory() {
        return producerFactoryFor("github");
    }

    @Bean(name = "githubKafkaTemplate")
    @ConditionalOnProperty(value = "feature.toggles.kafka.producers.oauth_provider.github.enabled", havingValue = "true")
    public KafkaTemplate<String, TradeEventMessage> githubKafkaTemplate() {
        return new KafkaTemplate<>(githubProducerFactory());
    }

    @Bean(name = "microsoftProducerFactory")
    @ConditionalOnProperty(value = "feature.toggles.kafka.producers.oauth_provider.microsoft.enabled", havingValue = "true")
    public ProducerFactory<String, TradeEventMessage> microsoftProducerFactory() {
        return producerFactoryFor("microsoft");
    }

    @Bean(name = "microsoftKafkaTemplate")
    @ConditionalOnProperty(value = "feature.toggles.kafka.producers.oauth_provider.microsoft.enabled", havingValue = "true")
    public KafkaTemplate<String, TradeEventMessage> microsoftKafkaTemplate() {
        return new KafkaTemplate<>(microsoftProducerFactory());
    }

    /**
     * Log all brokers loaded from configuration for quick visibility.
     */
    @PostConstruct
    public void logKafkaConfigs() {
        log.info("📡 Loaded Kafka brokers: {}", kafkaClusterConfig.getBrokers());
    }
}
