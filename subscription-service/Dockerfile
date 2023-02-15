FROM eclipse-temurin:17-jdk-focal

# Add Maintainer Info
LABEL maintainer="athanasia9410@gmail.com"

ADD build/libs/subscription-service-0.0.1-SNAPSHOT.jar subscription-service.jar
EXPOSE 5001
ENTRYPOINT ["java", "-jar","subscription-service.jar"]