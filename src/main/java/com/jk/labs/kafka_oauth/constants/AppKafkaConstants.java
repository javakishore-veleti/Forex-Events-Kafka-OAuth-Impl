package com.jk.labs.kafka_oauth.constants;

import java.util.List;

public class AppKafkaConstants {

    // "Auth0", "Okta", "AzureAD",
    public static final List<String> AUTH_PROVIDERS = List.of("Keycloak", "Github", "Google");

    // "okta", "azuread",
    public static final List<String> KAFKA_TOPIC_NAMES = List.of("keycloak", "microsoft", "github", "google");
}
