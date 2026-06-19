# Stage 1: Build the application using Maven
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
WORKDIR /app
# Copy the pom.xml and source code
COPY pom.xml .
COPY src ./src
# Build the application and skip tests (since the CI pipeline will run them separately)
RUN mvc clean package -DskipTests

# Stage 2: Run the application using a slim JRE runtime
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
# Copy the compiled .jar file from the build stage
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]