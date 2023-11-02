FROM eclipse-temurin:17

LABEL mentainer="hamoudiouchar@gmail.com"

WORKDIR /app

COPY target/springboot-rest-api-0.0.1-SNAPSHOT.jar /app/springboot-docker-demo.jar

ENTRYPOINT["java", "-jar", "springboot-docker-demo.jar"]

