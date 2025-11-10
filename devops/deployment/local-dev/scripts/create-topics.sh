#!/usr/bin/env bash
set -e

PROVIDER="kafkaDefault"
BROKER_HOST="kafkaDefault"
BROKER_PORT=9096

# Override with command-line arguments if provided
if [ $# -ge 1 ]; then
  PROVIDER="$1"
fi

if [ $# -ge 2 ]; then
  BROKER_HOST="$2"
fi

if [ $# -ge 3 ]; then
  BROKER_PORT="$3"
fi

# Display final values
echo "PROVIDER=$PROVIDER"
echo "BROKER_HOST=$BROKER_HOST"
echo "BROKER_PORT=$BROKER_PORT"


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
  "oauth.${PROVIDER}.errors
  "forex.${PROVIDER}.events"
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
