#!/bin/bash
set -e

echo "Waiting for all Kafka brokers to be ready..."

# Helper to wait until Kafka metadata responds
wait_for_broker() {
  local name=$1
  local host=$2
  local port=$3

  echo "⏳ Checking broker $name ($host:$port)..."
  for i in {1..30}; do
    if kafka-topics --bootstrap-server "${host}:${port}" --list >/dev/null 2>&1; then
      echo "$name is ready!"
      return
    fi
    echo "   still waiting ($i/30)..."
    sleep 3
  done
  echo "$name failed to respond in time."
  exit 1
}

# Wait for all brokers
wait_for_broker "Keycloak" kafka-keycloak 9093
wait_for_broker "Google" kafka-google 9094
wait_for_broker "GitHub" kafka-github 9095
wait_for_broker "Microsoft" kafka-microsoft 9096

echo "Creating topics for each provider..."

create_topics() {
  local name=$1
  local bootstrap=$2
  for topic in oauth.${name}.events oauth.${name}.audit oauth.${name}.errors; do
    echo "Creating topic ${topic} on ${bootstrap}"
    kafka-topics --create --if-not-exists \
      --topic "${topic}" \
      --bootstrap-server "${bootstrap}" \
      --partitions 3 \
      --replication-factor 1 || true
  done
}

create_topics "keycloak" kafka-keycloak:9093
create_topics "google" kafka-google:9094
create_topics "github" kafka-github:9095
create_topics "microsoft" kafka-microsoft:9096

echo "All topics created successfully!"
