package com.jk.labs.kafka_oauth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@SuppressWarnings("ALL")
@Configuration
@ConfigurationProperties(prefix = "feature.toggles.kafka")
public class KafkaFeatureToggles {

    private Map<String, ProviderToggle> oauth_provider;
    private Consumers consumers;
    private Producers producers;

    // inner classes
    @SuppressWarnings("LombokSetterMayBeUsed")
    public static class ProviderToggle {
        private boolean enabled;

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
    }

    @SuppressWarnings("LombokSetterMayBeUsed")
    public static class Consumers {
        private Map<String, ProviderToggle> oauth_provider;

        public Map<String, ProviderToggle> getOauth_provider() {
            return oauth_provider;
        }

        public void setOauth_provider(Map<String, ProviderToggle> oauth_provider) {
            this.oauth_provider = oauth_provider;
        }
    }

    @SuppressWarnings("LombokSetterMayBeUsed")
    public static class Producers {
        private Map<String, ProviderToggle> oauth_provider;

        public Map<String, ProviderToggle> getOauth_provider() {
            return oauth_provider;
        }

        public void setOauth_provider(Map<String, ProviderToggle> oauth_provider) {
            this.oauth_provider = oauth_provider;
        }
    }

    // getters/setters
    public Map<String, ProviderToggle> getOauth_provider() {
        return oauth_provider;
    }

    public void setOauth_provider(Map<String, ProviderToggle> oauth_provider) {
        this.oauth_provider = oauth_provider;
    }

    public Consumers getConsumers() {
        return consumers;
    }

    public void setConsumers(Consumers consumers) {
        this.consumers = consumers;
    }

    public Producers getProducers() {
        return producers;
    }

    public void setProducers(Producers producers) {
        this.producers = producers;
    }
}
