FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

COPY target/your-app.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]

EXPOSE 8080
