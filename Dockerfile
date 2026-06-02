FROM maven:3.9.7-eclipse-temurin-21 AS builder
WORKDIR /build
COPY pom.xml .
RUN mvn dependency:go-offline -q
COPY src ./src
RUN mvn clean package -DskipTests -q

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
RUN addgroup -S divenclasse && adduser -S divenclasse -G divenclasse
USER divenclasse
COPY --from=builder /build/target/inventory-service-*.jar app.jar
EXPOSE 8085
HEALTHCHECK --interval=30s --timeout=10s --start-period=40s --retries=3 \
  CMD wget -qO- http://localhost:8085/actuator/health || exit 1
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "app.jar"]
