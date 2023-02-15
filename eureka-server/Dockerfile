FROM eclipse-temurin:17-jdk-focal

# Add Maintainer Info
LABEL maintainer="athanasia9410@gmail.com"

ADD build/libs/eureka-server-0.0.1-SNAPSHOT.jar eureka-server.jar
EXPOSE 8070
ENTRYPOINT ["java", "-jar","eureka-server.jar"]