FROM openjdk:21-jdk-slim
ARG JAR_FILE=build/libs/webfluxSecurity-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app_webfluxSecurity.jar
EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "app_webfluxSecurity.jar"]