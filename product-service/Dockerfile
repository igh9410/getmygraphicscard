FROM eclipse-temurin:17-jdk-focal

# Add Maintainer Info
LABEL maintainer="athanasia9410@gmail.com"

# Add a volume pointing to /tmp
VOLUME /tmp

EXPOSE 5000



ADD build/libs/product-service-0.0.1-SNAPSHOT.jar product-service.jar






ENTRYPOINT ["java", "-jar","product-service.jar"]
