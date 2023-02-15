#stage 1
#Start with a base image containing Java runtime
FROM eclipse-temurin:17-jdk-focal

# Add Maintainer Info
LABEL maintainer="athanasia9410@gmail.com"

# Application's jar file
ARG JAR_FILE

# Add the application's jar to the container
COPY ${JAR_FILE} api-gateway.jar

#unpackage jar file

ADD build/libs/api-gateway-0.0.1-SNAPSHOT.jar api-gateway.jar
EXPOSE 8888
ENTRYPOINT ["java", "-jar","api-gateway.jar"]