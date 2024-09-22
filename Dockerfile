FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean install


FROM openjdk:17-alpine
WORKDIR /app

COPY --from=build  /app/target/prueba-lexart-0.0.1-SNAPSHOT.jar ./prueba-lexart-0.0.1-SNAPSHOT.jar
EXPOSE 8081
CMD ["java", "-jar", "prueba-lexart-0.0.1-SNAPSHOT.jar"]

