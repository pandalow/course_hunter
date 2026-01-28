FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
COPY course-hunter-common/pom.xml course-hunter-common/
COPY course-hunter-pojo/pom.xml course-hunter-pojo/
COPY course-hunter-server/pom.xml course-hunter-server/

RUN mvn dependency:go-offline

COPY . .

RUN mvn clean package -DskipTests -pl course-hunter-server -am

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /app/course-hunter-server/target/*.jar app.jar
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]