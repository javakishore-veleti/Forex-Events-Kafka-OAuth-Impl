#!/bin/bash
set -e

echo "Waiting for Hydra to be ready..."
sleep 10

# Create OAuth client for Kafka broker
echo "Creating Kafka broker OAuth client..."
curl -f -X POST http://ordy-hydra:4445/admin/clients \
  -H "Content-Type: application/json" \
  -d '{
    "client_id": "kafka-broker",
    "client_secret": "kafka-secret",
    "grant_types": ["client_credentials", "refresh_token"],
    "response_types": ["token"],
    "scope": "openid offline",
    "token_endpoint_auth_method": "client_secret_post"
  }'

# Create OAuth client for Kafka producers/consumers
echo "Creating Kafka client OAuth client..."
curl -f -X POST http://ordy-hydra:4445/admin/clients \
  -H "Content-Type: application/json" \
  -d '{
    "client_id": "kafka-client",
    "client_secret": "client-secret",
    "grant_types": ["client_credentials", "refresh_token"],
    "response_types": ["token"],
    "scope": "openid offline",
    "token_endpoint_auth_method": "client_secret_post"
  }'

echo "OAuth clients created successfully!"