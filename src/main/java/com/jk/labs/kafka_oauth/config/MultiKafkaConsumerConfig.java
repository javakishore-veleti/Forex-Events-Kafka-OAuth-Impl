package com.jk.labs.kafka_oauth.config;

import com.jk.labs.kafka_oauth.dto.TradeEventMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
public class MultiKafkaConsumerConfig {

    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    private KafkaClusterConfig kafkaClusterConfig;

    private Map<String, Object> baseConsumerProps(String bootstrap) {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrap);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        return props;
    }

    private ConsumerFactory<String, TradeEventMessage> consumerFactoryFor(String provider) {
        String bootstrap = kafkaClusterConfig.getBootstrap(provider);
        if (bootstrap == null || bootstrap.isBlank()) {
            log.warn("No Kafka bootstrap found for provider '{}'. Falling back to localhost:9093", provider);
            bootstrap = "localhost:9093";
        }

        Map<String, Object> props = baseConsumerProps(bootstrap);

        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, TradeEventMessage.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.jk.labs.kafka_oauth.dto");

        // Apply SASL/OAUTHBEARER settings
        addSaslOauthBearerProps(props, provider);

        log.info("Kafka ConsumerFactory created for '{}' → bootstrap.servers={}", provider, bootstrap);
        //return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), deserializer);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    private ConcurrentKafkaListenerContainerFactory<String, TradeEventMessage> listenerFactoryFor(String provider) {
        ConcurrentKafkaListenerContainerFactory<String, TradeEventMessage> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryFor(provider));
        factory.setAutoStartup(true);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        factory.setConcurrency(1);
        return factory;
    }

    @Bean(name = "keycloakKafkaListenerContainerFactory")
    @ConditionalOnProperty(value = "feature.toggles.kafka.consumers.oauth_provider.keycloak.enabled", havingValue = "true")
    public ConcurrentKafkaListenerContainerFactory<String, TradeEventMessage> keycloakKafkaListenerContainerFactory() {
        return listenerFactoryFor("keycloak");
    }

    @Bean(name = "googleKafkaListenerContainerFactory")
    @ConditionalOnProperty(value = "feature.toggles.kafka.consumers.oauth_provider.google.enabled", havingValue = "true")
    public ConcurrentKafkaListenerContainerFactory<String, TradeEventMessage> googleKafkaListenerContainerFactory() {
        return listenerFactoryFor("google");
    }

    @Bean(name = "githubKafkaListenerContainerFactory")
    @ConditionalOnProperty(value = "feature.toggles.kafka.consumers.oauth_provider.github.enabled", havingValue = "true")
    public ConcurrentKafkaListenerContainerFactory<String, TradeEventMessage> githubKafkaListenerContainerFactory() {
        return listenerFactoryFor("github");
    }

    @Bean(name = "microsoftKafkaListenerContainerFactory")
    @ConditionalOnProperty(value = "feature.toggles.kafka.consumers.oauth_provider.microsoft.enabled", havingValue = "true")
    public ConcurrentKafkaListenerContainerFactory<String, TradeEventMessage> microsoftKafkaListenerContainerFactory() {
        return listenerFactoryFor("microsoft");
    }

    private void addSaslOauthBearerProps(Map<String, Object> props, String provider) {
        Map<String, String> oauthConfig = kafkaClusterConfig.getOauthConfig(provider);
        if (oauthConfig == null) return;

        props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SASL_SSL");
        props.put(SaslConfigs.SASL_MECHANISM, "OAUTHBEARER");
        props.put(SaslConfigs.SASL_JAAS_CONFIG,
                "org.apache.kafka.common.security.oauthbearer.OAuthBearerLoginModule required;");
        props.put(SaslConfigs.SASL_LOGIN_CALLBACK_HANDLER_CLASS,
                "org.apache.kafka.common.security.oauthbearer.secured.OAuthBearerLoginCallbackHandler");

        props.put("oauth.token.endpoint.uri", oauthConfig.get("token-endpoint"));
        props.put("oauth.client.id", oauthConfig.get("client-id"));
        props.put("oauth.client.secret", oauthConfig.get("client-secret"));

        props.put("sasl.oauthbearer.token.endpoint.url", oauthConfig.get("token-endpoint"));
        props.put("sasl.oauthbearer.client.id", oauthConfig.get("client-id"));
        props.put("sasl.oauthbearer.clientId", oauthConfig.get("client-id"));
        props.put("sasl.oauthbearer.client.secret", oauthConfig.get("client-secret"));
        props.put("sasl.oauthbearer.clientSecret", oauthConfig.get("client-secret"));

        String jassConfig = "org.apache.kafka.common.security.oauthbearer.OAuthBearerLoginModule required ";
        jassConfig = jassConfig + "scope='BrokerApplicationId/.default' ";
        jassConfig = jassConfig + "clientId='" + oauthConfig.get("client-id") + "' ";
        jassConfig = jassConfig + "clientSecret='" + oauthConfig.get("client-secret") + "' ;";
        props.put(SaslConfigs.SASL_JAAS_CONFIG, jassConfig);
    }
}
