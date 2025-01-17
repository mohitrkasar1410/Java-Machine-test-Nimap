# service-registry/Dockerfile
FROM maven:3.8.5-openjdk-17-slim AS builder

WORKDIR /app

# Copy pom.xml and source files
COPY pom.xml ./
COPY src ./src/

# Build the application
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy the built JAR file from the builder stage
COPY --from=builder /app/target/service-registry-*.jar service-registry.jar

# Expose port 8761
EXPOSE 8761

# Run the application
CMD ["java", "-jar", "service-registry.jar"]
