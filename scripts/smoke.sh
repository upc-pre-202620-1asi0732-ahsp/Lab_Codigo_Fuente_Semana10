#!/usr/bin/env bash
set -euo pipefail
URL="${1:-http://127.0.0.1:8080}"
for i in $(seq 1 30); do
 if curl --fail --silent "${URL}/actuator/health" | grep -q '"UP"'; then
   curl --fail --silent "${URL}/" >/dev/null
   echo "SMOKE PASS: ${URL}"
   exit 0
 fi
 sleep 2
done
echo "SMOKE FAIL: ${URL}" >&2
exit 1
