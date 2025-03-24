FROM  maven:3.9.9-eclipse-temurin-17-alpine AS maven
WORKDIR /song-server
COPY pom.xml .
COPY src ./src
RUN mvn dependency:go-offline
RUN mvn clean package
FROM eclipse-temurin:17-jre-alpine-3.21
WORKDIR /song-server
COPY --from=maven /song-server/target/song-server-0.0.1-SNAPSHOT.jar .
CMD ["java", "-jar", "song-server-0.0.1-SNAPSHOT.jar"]
