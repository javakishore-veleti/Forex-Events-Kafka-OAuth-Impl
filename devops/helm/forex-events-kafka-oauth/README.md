# Helm Chart — forex-events-kafka-oauth

Deploys the **Forex Events Kafka OAuth** Spring Boot microservice with Kafka, OAuth2, and OpenTelemetry.

## 🧩 Installation

### Local / Dev

```bash
helm upgrade --install forex-events-kafka-oauth devops/helm/forex-events-kafka-oauth \
  -f values-dev.yaml -n dev --create-namespace

```

### Staging

```bash
helm upgrade --install forex-events-kafka-oauth devops/helm/forex-events-kafka-oauth \
  -f values-stg.yaml -n stg --create-namespace
```

### Production

```bash
helm upgrade --install forex-events-kafka-oauth devops/helm/forex-events-kafka-oauth \
  -f values-prod.yaml -n prod --create-namespace
```

## Verification

```shell
helm lint devops/helm/forex-events-kafka-oauth
helm template forex-events-kafka-oauth devops/helm/forex-events-kafka-oauth | less
kubectl get pods -n dev

```

## Summary

| Component                   | Final Value                          |
|-----------------------------|--------------------------------------|
| **Helm Chart Name**         | `forex-events-kafka-oauth`           |
| **Docker Image Name**       | `docker.io/forex-events-kafka-oauth` |
| **Service Name (K8s)**      | `forex-events-kafka-oauth-svc`       |
| **ConfigMap**               | `forex-events-kafka-oauth-config`    |
| **OTEL Service Name (env)** | `forex-events-kafka-oauth-<env>`     |

