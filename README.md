# BBVA Digital Banking Lab (simulador académico)

Proyecto independiente para el curso Diseño de Experimentos de Ingeniería de Software (UPC). No es una aplicación oficial de BBVA. No usa credenciales, clientes ni operaciones reales.

## macOS
1. Instalar Homebrew, JDK 21, Maven, Git y Docker Desktop.
2. Verificar `java -version`, `mvn -version`, `docker version`.
3. `mvn clean verify` y abrir `target/site/jacoco/index.html`.
4. `mvn spring-boot:run` y abrir `http://localhost:8080`.
5. Detener Java, ejecutar `mvn clean package`, `docker build -t bbva-lab:1.0 .`, `docker run -d --name bbva-lab -p 8080:8080 bbva-lab:1.0`.
6. `bash scripts/smoke.sh`.
7. `docker login`, `docker tag bbva-lab:1.0 USUARIO/bbva-digital-banking-lab:1.0`, `docker push USUARIO/bbva-digital-banking-lab:1.0`.
8. `docker rm -f bbva-lab`, `docker image rm USUARIO/bbva-digital-banking-lab:1.0`, `docker pull USUARIO/bbva-digital-banking-lab:1.0`.
9. `docker run -d --name bbva-lab -p 8080:8080 USUARIO/bbva-digital-banking-lab:1.0` y `bash scripts/smoke.sh`.

## GitHub Actions
Configurar secretos `SONAR_TOKEN`, `DOCKERHUB_USERNAME`, `DOCKERHUB_TOKEN`, `DEPLOY_HOST`, `DEPLOY_USER`, `DEPLOY_SSH_KEY`, `DEPLOY_HOST_KEY`; variables `SONAR_ORG`, `SONAR_PROJECT_KEY`.
En SonarQube Cloud importar el repositorio y habilitar Quality Gate. Crear un entorno GitHub `lab-production` (opcional: requerir aprobación para demostrar Continuous Delivery). La configuración incluida realiza despliegue automático tras push a main.

## Ubuntu remoto
Instalar Docker Engine por el procedimiento oficial. Crear un usuario de despliegue dedicado con permisos Docker (privilegios elevados: VM exclusiva de laboratorio). Instalar su clave pública en `~/.ssh/authorized_keys`.
Clonar el repositorio en `~/bbva-digital-banking-lab` y ejecutar `chmod +x scripts/*.sh`.
El script de despliegue acepta `usuario/imagen@sha256:digest`; verifica candidato en 127.0.0.1:18080 y luego reemplaza servicio en 127.0.0.1:8080. Si falla la verificación final, intenta rollback. Existe una breve interrupción en el cambio de contenedor. Para acceder desde macOS usar `ssh -L 8080:127.0.0.1:8080 usuario@HOST` con puerto local libre.

## Experimentos
A: introducir error controlado en `TransferService` (cambiar `subtract` por `add`), comprobar prueba fallida. B: restaurar código, comprobar que Quality Gate y smoke test pasan. Registrar commit, duración, tests, cobertura, calidad y resultado de despliegue. La aplicación guarda estado solo en memoria; reiniciar contenedor reinicia saldo y movimientos.

## Advertencias
No subir tokens ni claves privadas. La imagen es pública solo si el repositorio Docker Hub es público. El workflow necesita credenciales y servidor antes de ejecutarse de extremo a extremo. No usar esta app como sistema bancario real. Dependencias y acciones deben actualizarse y fijarse por SHA antes de usos sensibles.
