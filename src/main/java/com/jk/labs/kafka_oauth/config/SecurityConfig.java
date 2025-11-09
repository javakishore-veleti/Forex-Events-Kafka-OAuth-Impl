package com.jk.labs.kafka_oauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // Disable CSRF (not needed for local APIs)
                .csrf(csrf -> csrf.disable())
                // Disable all authentication
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                // Disable default login, logout, etc.
                .oauth2Login(oauth -> oauth.disable())
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable());

        return http.build();
    }
}
