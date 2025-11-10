package com.kv.labs.kafka_oauth.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Forex Events Kafka OAuth API")
                        .version("1.0.0")
                        .description("Reference implementation for Kafka OAuth providers and event tracing."));
    }
}