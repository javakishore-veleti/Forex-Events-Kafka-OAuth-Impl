#!/bin/bash
set -e

DEV_REPO_PATH=$(pwd)
QUALITY_REPO_PATH=../quality-forex

echo "Syncing approved changes from dev -> quality..."
rsync -av --delete \
  --exclude '.git' \
  --exclude 'target' \
  --exclude '.idea' \
  --exclude '.vscode' \
  --exclude '*.iml' \
  ${DEV_REPO_PATH}/ ${QUALITY_REPO_PATH}/

cd ${QUALITY_REPO_PATH}
git add .
git commit -m "Incremental update from dev repo on $(date)"
git push origin main

echo "Successfully published incremental update to quality repo."
