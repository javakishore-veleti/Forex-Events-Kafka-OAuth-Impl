#!/usr/bin/env bash
set -e

PROVIDER="microsoft"
BROKER_HOST="kafka-microsoft"
BROKER_PORT=9096

echo "🔄 Waiting for ${PROVIDER^} Kafka broker (${BROKER_HOST}:${BROKER_PORT}) to be ready..."

for i in {1..30}; do
  if kafka-topics --bootstrap-server "${BROKER_HOST}:${BROKER_PORT}" --list >/dev/null 2>&1; then
    echo "✅ ${PROVIDER^} broker is ready!"
    break
  fi
  echo "   ⏳ Waiting for ${PROVIDER^} broker... (${i}/30)"
  sleep 3
done

echo "📦 Creating topics for ${PROVIDER^}..."

TOPICS=(
  "oauth.${PROVIDER}.events"
  "oauth.${PROVIDER}.audit"
  "oauth.${PROVIDER}.errors"
  "forex-${PROVIDER}-topic"
)

for topic in "${TOPICS[@]}"; do
  echo "➡️  Creating topic ${topic}"
  kafka-topics --create --if-not-exists \
    --topic "${topic}" \
    --bootstrap-server "${BROKER_HOST}:${BROKER_PORT}" \
    --partitions 3 \
    --replication-factor 1 || true
done

echo "✅ All ${PROVIDER^} topics created successfully!"
