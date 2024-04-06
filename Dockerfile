FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
COPY target/droneManagementApp-0.0.1-SNAPSHOT.jar /app
EXPOSE 3000
ENTRYPOINT ["java","-jar","/app.jar", ".jar"]