FROM  maven:3.9.9-eclipse-temurin-17-alpine AS maven
WORKDIR /discovery-service
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package
FROM eclipse-temurin:17-jre-alpine-3.21
WORKDIR /discovery-service
COPY --from=maven /discovery-service/target/*.jar app.jar
EXPOSE 8761
CMD ["java", "-jar", "app.jar"]