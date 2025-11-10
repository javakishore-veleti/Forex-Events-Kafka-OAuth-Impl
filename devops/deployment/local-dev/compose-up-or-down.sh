#!/usr/bin/env bash
# -----------------------------------------------------------------------------
# compose-up-or-down.sh
#
# Runs docker compose with optional profiles based on the environment variable:
#   KAFKA_OAUTHBEARER_IMPL_PROFILES
#
# Examples:
#   ./compose-up-or-down.sh up
#   ./compose-up-or-down.sh down
#   ./compose-up-or-down.sh logs -f
#
# If KAFKA_OAUTHBEARER_IMPL_PROFILES is not set, runs only default (non-profiled)
# services. If set (e.g. "microsoft,google"), runs only those profiles + defaults.
# -----------------------------------------------------------------------------

set -e

# Use first argument as command (default = up)
CMD=${1:-up}
shift || true  # remove the command argument so extra flags pass through

# compose-up-or-down.sh — run docker compose with optional profiles

# This script checks for KAFKA_OAUTHBEARER_IMPL_PROFILES.
# If it’s set, it splits it by commas and runs those profiles;
# if it’s empty, it starts only the default (non-profiled) services.

# Example: run microsoft and google profiles
# export KAFKA_OAUTHBEARER_IMPL_PROFILES="microsoft,google"

# Example: run defaults only
# unset KAFKA_OAUTHBEARER_IMPL_PROFILES

# Check if the env variable is set
if [ -z "$KAFKA_OAUTHBEARER_IMPL_PROFILES" ]; then
  echo "No profiles set (KAFKA_OAUTHBEARER_IMPL_PROFILES not defined)."
  echo "   Running default (non-profiled) services with: docker compose $CMD $*"
  docker compose "$CMD" "$@"
else
  echo "Using profiles: $KAFKA_OAUTHBEARER_IMPL_PROFILES"
  PROFILE_ARGS=$(echo "$KAFKA_OAUTHBEARER_IMPL_PROFILES" | awk -F, '{for(i=1;i<=NF;i++) printf "--profile %s ", $i}')
  echo "   Running: docker compose $PROFILE_ARGS $CMD $*"
  docker compose $PROFILE_ARGS "$CMD" "$@"
fi
