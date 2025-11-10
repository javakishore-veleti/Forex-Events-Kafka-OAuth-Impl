# Forex Events Kafka OAuth Implementation

[![Build Docker](https://github.com/javakishore-veleti/Forex-Events-Kafka-OAuth-Impl/actions/workflows/build-docker.yml/badge.svg)](https://github.com/javakishore-veleti/Forex-Events-Kafka-OAuth-Impl/actions/workflows/build-docker.yml)
[![Release Helm](https://github.com/javakishore-veleti/Forex-Events-Kafka-OAuth-Impl/actions/workflows/release-helm.yml/badge.svg)](https://github.com/javakishore-veleti/Forex-Events-Kafka-OAuth-Impl/actions/workflows/release-helm.yml)
[![Deploy K8s](https://github.com/javakishore-veleti/Forex-Events-Kafka-OAuth-Impl/actions/workflows/deploy-kubernetes.yml/badge.svg)](https://github.com/javakishore-veleti/Forex-Events-Kafka-OAuth-Impl/actions/workflows/deploy-kubernetes.yml)
[![Rollback](https://github.com/javakishore-veleti/Forex-Events-Kafka-OAuth-Impl/actions/workflows/rollback-on-failure.yml/badge.svg)](https://github.com/javakishore-veleti/Forex-Events-Kafka-OAuth-Impl/actions/workflows/rollback-on-failure.yml)
[![Version Release](https://github.com/javakishore-veleti/Forex-Events-Kafka-OAuth-Impl/actions/workflows/release-version.yml/badge.svg)](https://github.com/javakishore-veleti/Forex-Events-Kafka-OAuth-Impl/actions/workflows/release-version.yml)

## Overview

**Forex Events Kafka OAuth Implementation** is an enterprise-grade, open-source reference project demonstrating:

- Primary focus is to showcase Spring Boot + kafka + OAUTH Bearer for Kafka Procuers and Consumers
- Also show case Kafka Listener(s) with different Kafka Bootstrap servers within the single Spring Boot application
- Real-time **Forex trade event streaming** via **Apache Kafka**
- **OAuth2/OIDC-secured Kafka clients** (Azure, Google, Github, Okta, Auth0 )
- **Spring Boot 3 + JDK 17**
- **OpenTelemetry + Prometheus + Grafana + Jaeger** observability
- **Multi-broker**, **multi-provider** design with feature toggles
- **Docker**, **Helm**, **GitHub Actions** CI/CD with auto-rollback
- **Staged deployments** (staging → production) via GitHub Environments

## Tech Stack

| Category             | Tools                                 |
|----------------------|---------------------------------------|
| **Core**             | Java 17, Spring Boot 3, Lombok, SLF4J |
| **Streaming**        | Apache Kafka (multi-broker)           |
| **Security**         | SASL/OAUTHBEARER with OAuth2/OIDC     |
| **Data**             | H2 + PostgreSQL (multi-datasource)    |
| **Metrics**          | OpenTelemetry, Micrometer, Prometheus |
| **Tracing**          | Jaeger                                |
| **Visualization**    | Grafana (auto-provisioned dashboard)  |
| **CI/CD**            | GitHub Actions, Helm, Docker          |
| **Containerization** | Docker, Compose, Kubernetes           |

## Repository Structure

```text
Forex-Events-Kafka-OAuth-Impl/
├── src/main/java/com/kv/labs/kafka_oauth/
│ ├── api/ForexTradeController.java
│ ├── service/ForexTradePublisherService.java
│ ├── oauth/InstrumentedOAuthBearerLoginCallbackHandler.java
│ ├── consumer/{Keycloak,Okta,Auth0,Azure}ForexConsumer.java
│ ├── repository/{H2,Postgres}TradeRepository.java
│ ├── metrics/OAuthKafkaMetricsService.java
│ ├── config/{Kafka,OTEL,MultiBroker}Config.java
│ └── ForexKafkaOauthApplication.java
│
├── devops/
│ ├── dockerfiles/
│ ├── docker-compose.local.yml
│ ├── env/{dev,qa,stg,prod}/.env
│ └── helm/kishore-veleti-forex-oauth-kafka/
│
└── .github/
├── workflows/
│ ├── build-docker.yml
│ ├── release-helm.yml
│ ├── deploy-kubernetes.yml
│ ├── rollback-on-failure.yml
│ ├── release-version.yml
│ ├── release-drafter.yml
│ └── pr-labeler.yml
├── labeler.yml
└── release-drafter.yml
```

## Local Development

### 1. Build

```bash
mvn clean package -DskipTests

#docker-compose -f devops/docker-compose.local.yml up --build
npm run infra:down
npm run infra:up

```

```text
Above "npm run" command spins up:

- Kafka brokers
- Keycloak OAuth
- Prometheus, Grafana, Jaeger

```

```bash
# Command to start the Spring Boot application
java -jar target/forex-events-kafka-oauth-impl-*.jar

```


## Publish Random Forex Trades

Publishes 10 random Forex trades by default. Each enabled provider consumes from its topic and stores data (
H2/Postgres).

```shell

curl -X GET "http://localhost:8080/api/v1/fx-trade-events/publish"

```

## Feature Toggles

In application.yml or Helm values. Only toggled-on providers connect to brokers and authenticate.

```yaml
features:
  kafkaDefault.enabled: true
  keycloak.enabled: false
  okta.enabled: false
  auth0.enabled: true
  azure.enabled: true

```

## Observability Stack

| Component      | URL                                              | Purpose                     |
|----------------|--------------------------------------------------|-----------------------------|
| Grafana        | [http://localhost:3000](http://localhost:3000)   | Auto-provisioned dashboards |
| Prometheus     | [http://localhost:9090](http://localhost:9090)   | Metric scraping             |
| Jaeger         | [http://localhost:16686](http://localhost:16686) | Distributed traces          |
| OTEL Collector | :4317                                            | OpenTelemetry pipeline      |

## Metrics Overview

```shell
# OAuth metrics
oauth_auth_success_total{provider="keycloak"} 5
oauth_auth_failure_total{provider="okta"} 0
oauth_kafka_connection_latency_seconds_sum{provider="auth0"} 1.02

# Forex metrics
forex_trades_published_total{provider="azure"} 10
forex_consume_latency_seconds_sum{provider="keycloak"} 1.23
```

## GitHub Actions CI/CD

| Workflow            | Description                            |
|---------------------|----------------------------------------|
| **Build Docker**    | Builds & pushes all images             |
| **Release Helm**    | Packages and publishes chart           |
| **Deploy K8s**      | Deploys to staging / production        |
| **Smoke Test**      | API + metrics validation               |
| **Rollback**        | Auto rollback on failure               |
| **Version Release** | Semantic tagging & releases            |
| **Release Drafter** | Auto-generated changelog               |
| **PR Labeler**      | Auto-labels PRs for changelog grouping |

## Environments & Secrets

| Env          | Secrets                                                              | Purpose                   |
|--------------|----------------------------------------------------------------------|---------------------------|
| `staging`    | `KUBECONFIG_CONTENTS`, `SLACK_WEBHOOK_URL`                           | Auto-deploy after release |
| `production` | `KUBECONFIG_CONTENTS`, `SLACK_WEBHOOK_URL`, `GRAFANA_ADMIN_PASSWORD` | Manual approval required  |

## Architecture Diagram

```text
                 ┌─────────────────────┐
                 │ ForexTradePublisher │
                 └──────────┬──────────┘
                            │
           ┌────────────────┴────────────────┐
           │ Multi Kafka Brokers (Providers) │
           │  Keycloak, Okta, Auth0, Azure   │
           └────────────────┬────────────────┘
                            │
           ┌────────────────┴────────────────┐
           │ OAuthBearerLoginCallbackHandler │
           │  + OTEL metrics/traces          │
           └────────────────┬────────────────┘
                            │
          ┌─────────────────┴─────────────────┐
          │ OpenTelemetry Collector           │
          │ Prometheus │ Jaeger │ Grafana     │
          └───────────────────────────────────┘

```

## Goals
- Reference implementation of OAuth-secured Kafka-
- Demonstrate OTEL metrics for publish & consume paths
- Show multi-broker Kafka + feature toggles
- End-to-end CI/CD, rollback, and observability



