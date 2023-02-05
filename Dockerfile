# FROM eclipse-temurin:19-alpine
 
# WORKDIR /app
 
# COPY .mvn/ .mvn
# COPY mvnw pom.xml ./
# RUN ./mvnw dependency:go-offline
 
# COPY src ./src
 
# CMD ["./mvnw", "spring-boot:run", "-Dspring-boot.run.jvmArguments=-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=6666"]

FROM maven:3.8.7-eclipse-temurin-19 AS MAVEN_BUILD

COPY pom.xml /build/
COPY src /build/src/

WORKDIR /build/
RUN mvn package

FROM eclipse-temurin:19-alpine

WORKDIR /app

COPY --from=MAVEN_BUILD /build/target/graphql-postcode-api-integration-0.0.1.jar /app/graphql-postcode-api.jar

ENTRYPOINT ["java", "-jar", "graphql-postcode-api.jar"]