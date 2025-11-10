package com.jk.labs.kafka_oauth.constants;

import java.util.List;
import java.util.Map;

public class AppKafkaConstants {

    // "Auth0", "Okta", "AzureAD",
    // List of all supported auth providers
    public static final List<String> AUTH_PROVIDERS = List.of(
            "kafkaDefault",
            "keycloak",
            "google",
            "github",
            "microsoft"
    );

    // "okta", "azuread",
    // Default Kafka topic names for each provider
    public static final List<String> KAFKA_TOPIC_NAMES = List.of(
            "forex-kafkaDefault-topic",
            "forex-keycloak-topic",
            "forex-google-topic",
            "forex-github-topic",
            "forex-microsoft-topic"
    );

    // Mapping of provider → topic name
    public static final Map<String, String> KAFKA_TOPIC_MAP = Map.of(
            "kafkaDefault", "forex-kafkaDefault-topic",
            "keycloak", "forex-keycloak-topic",
            "google", "forex-google-topic",
            "github", "forex-github-topic",
            "microsoft", "forex-microsoft-topic"
    );

    // Optional special-purpose topics
    public static final String AUDIT_TOPIC = "forex-audit-topic";
    public static final String ERROR_TOPIC = "forex-error-topic";

    // Default Kafka consumer group
    public static final String DEFAULT_GROUP_ID = "forex-group";

    // Common prefix (useful if you want to standardize topic naming)
    public static final String TOPIC_PREFIX = "forex-";
}
