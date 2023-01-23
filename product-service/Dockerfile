FROM eclipse-temurin:17-jdk-focal

# Add Maintainer Info
LABEL maintainer="athanasia9410@gmail.com"

ADD build/libs/product-service-0.0.1-SNAPSHOT.jar product-service.jar
EXPOSE 5000
ENTRYPOINT ["java", "-jar","product-service.jar"]
