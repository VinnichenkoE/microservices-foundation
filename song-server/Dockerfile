FROM  maven:3.9.9-eclipse-temurin-17-alpine AS maven
WORKDIR /song-service
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package
FROM eclipse-temurin:17-jre-alpine-3.21
WORKDIR /song-service
COPY --from=maven /song-service/target/*.jar app.jar
EXPOSE 8082
CMD ["java", "-jar", "app.jar"]
