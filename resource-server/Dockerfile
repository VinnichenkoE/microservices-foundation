FROM  maven:3.9.9-eclipse-temurin-17-alpine AS maven
WORKDIR /resource-server
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package
FROM eclipse-temurin:17-jre-alpine-3.21
WORKDIR /resource-server
COPY --from=maven /resource-server/target/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
