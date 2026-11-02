#!/usr/bin/env bash
set -euo pipefail
IMAGE="${1:?Uso: deploy.sh usuario/repo@sha256:digest}"
APP=bbva-digital-banking-lab
PREVIOUS="$(docker inspect --format '{{.Config.Image}}' "$APP" 2>/dev/null || true)"
docker pull "$IMAGE"
docker rm -f "${APP}-candidate" >/dev/null 2>&1 || true
docker run -d --name "${APP}-candidate" -p 127.0.0.1:18080:8080 "$IMAGE" >/dev/null
if ! bash "$HOME/bbva-digital-banking-lab/scripts/smoke.sh" http://127.0.0.1:18080; then
 docker logs "${APP}-candidate" || true
 docker rm -f "${APP}-candidate" >/dev/null
 exit 1
fi
docker rm -f "${APP}-candidate" >/dev/null
docker rm -f "$APP" >/dev/null 2>&1 || true
docker run -d --name "$APP" --restart unless-stopped -p 127.0.0.1:8080:8080 "$IMAGE" >/dev/null
if ! bash "$HOME/bbva-digital-banking-lab/scripts/smoke.sh" http://127.0.0.1:8080; then
 docker logs "$APP" || true
 docker rm -f "$APP" >/dev/null
 if [[ -n "$PREVIOUS" ]]; then docker run -d --name "$APP" --restart unless-stopped -p 127.0.0.1:8080:8080 "$PREVIOUS" >/dev/null; fi
 exit 1
fi
echo "DEPLOY OK: $IMAGE"
