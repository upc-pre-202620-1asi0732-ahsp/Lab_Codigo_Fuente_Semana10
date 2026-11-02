FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY target/bbva-digital-banking-lab-1.0.0.jar app.jar
EXPOSE 8080
USER 10001
ENTRYPOINT ["java","-jar","/app/app.jar"]
