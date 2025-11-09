#!/bin/bash
set -e

echo "Waiting for Kafka broker (kafka:9092)..."
for i in {1..30}; do
  if kafka-topics --bootstrap-server kafka:9092 --list > /dev/null 2>&1; then
    echo "Kafka is ready!"
    break
  fi
  echo "Waiting for Kafka... ($i/30)"
  sleep 2
done

echo "Creating OAuth reference topics..."
TOPICS=(
  "oauth.keycloak.events"
  "oauth.google.events"
  "oauth.github.events"
  "oauth.audit"
  "oauth.errors"
)

for topic in "${TOPICS[@]}"; do
  echo "➡Creating topic: $topic"
  kafka-topics \
    --create --if-not-exists \
    --topic "$topic" \
    --bootstrap-server kafka:9092 \
    --partitions 3 \
    --replication-factor 1 || true
done

echo "All provider topics created successfully!"
sleep 2
