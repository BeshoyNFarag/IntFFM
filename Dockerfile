# Stage 1: Build the application using Maven with Java 21
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copy the Maven project file and download dependencies
COPY MIntFFM/MIntFFM/pom.xml .
RUN mvn dependency:go-offline

# Copy the rest of your source code and build the JAR file
COPY MIntFFM/MIntFFM/src ./src
RUN mvn clean package -DskipTests

# Stage 2: Create the final image with Java 21
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy the built JAR file from the 'build' stage to this final image
COPY --from=build /app/target/*.jar ./app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]