# Builder stage
FROM gradle:7.2-jdk17 AS builder

# Copy your source code
COPY src ./src
COPY build.gradle settings.gradle ./

# Build the application
RUN gradle clean build --no-daemon

# Runner stage
FROM eclipse-temurin:17-jdk-focal

LABEL maintainer="athanasia9410@gmail.com"

# Copy the built jar from the builder stage
COPY --from=builder /home/gradle/build/libs/product-service-0.0.1-SNAPSHOT.jar product-service.jar

# Expose the application's port
EXPOSE 5000

# Run the application
ENTRYPOINT ["java", "-jar","product-service.jar"]
