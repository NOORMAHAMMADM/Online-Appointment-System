# ==========================================
# Multi-stage Dockerfile for Spring Boot
# ==========================================

# Stage 1: Build the application
FROM eclipse-temurin:17-jdk-jammy AS builder
WORKDIR /app

# Copy Maven wrapper and configuration for layer caching
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN chmod +x ./mvnw

# Pre-fetch dependencies
RUN ./mvnw dependency:go-offline -B -DskipFrontendBuild=true || true

# Copy source code and build the application JAR
COPY src/ ./src/
RUN ./mvnw clean package -B -DskipTests -DskipFrontendBuild=true

# Stage 2: Lightweight JRE runtime
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Create a non-privileged user for security
RUN useradd -m -u 1001 appuser && chown -R appuser:appuser /app
USER appuser

# Copy built JAR from builder stage
COPY --from=builder /app/target/*.jar app.jar

# Render injects $PORT dynamically (defaulting to 8080 locally)
ENV PORT=8080
EXPOSE 8080

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]

