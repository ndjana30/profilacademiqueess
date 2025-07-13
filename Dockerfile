#
# Build stage
#
FROM maven:3.8.2-eclipse-temurin-17 AS build
COPY . .
RUN mvn clean package -DskipTests

#
# Package stage
#
FROM openjdk:17-jdk-alpine
COPY --from=build /target/student-0.0.1-SNAPSHOT.jar student.jar

# ENV PORT=8080
EXPOSE 8080
ENTRYPOINT ["java","-jar","student.jar"]