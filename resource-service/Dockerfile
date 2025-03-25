FROM  maven:3.9.9-eclipse-temurin-17-alpine AS maven
WORKDIR /resource-service
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package
FROM eclipse-temurin:17-jre-alpine-3.21
WORKDIR /resource-service
COPY --from=maven /resource-service/target/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
