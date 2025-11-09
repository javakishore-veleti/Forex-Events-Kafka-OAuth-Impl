package com.jk.labs.kafka_oauth.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class MultiKafkaConfig {

    @Autowired
    private KafkaClusterConfig kafkaClusterConfig;

    private Map<String, Object> baseProducerProps() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return props;
    }

    private ProducerFactory<String, String> producerFactoryFor(String provider) {
        Map<String, Object> props = new HashMap<>(baseProducerProps());
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaClusterConfig.getBootstrap(provider));
        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean(name = "keycloakKafkaTemplate")
    @ConditionalOnProperty(value = "feature.toggles.kafka.producers.oauth_provider.keycloak.enabled", havingValue = "true", matchIfMissing = false)
    public KafkaTemplate<String, String> keycloakKafkaTemplate() {
        return new KafkaTemplate<>(producerFactoryFor("keycloak"));
    }

    @Bean(name = "googleKafkaTemplate")
    @ConditionalOnProperty(value = "feature.toggles.kafka.producers.oauth_provider.google.enabled", havingValue = "true", matchIfMissing = false)
    public KafkaTemplate<String, String> googleKafkaTemplate() {
        return new KafkaTemplate<>(producerFactoryFor("google"));
    }

    @Bean(name = "githubKafkaTemplate")
    @ConditionalOnProperty(value = "feature.toggles.kafka.producers.oauth_provider.github.enabled", havingValue = "true", matchIfMissing = false)
    public KafkaTemplate<String, String> githubKafkaTemplate() {
        return new KafkaTemplate<>(producerFactoryFor("github"));
    }

    @Bean(name = "microsoftKafkaTemplate")
    @ConditionalOnProperty(value = "feature.toggles.kafka.producers.oauth_provider.microsoft.enabled", havingValue = "true", matchIfMissing = false)
    public KafkaTemplate<String, String> microsoftKafkaTemplate() {
        return new KafkaTemplate<>(producerFactoryFor("microsoft"));
    }
}
