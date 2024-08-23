# Stage 1: Build the application
FROM eclipse-temurin:22-jdk-jammy AS build
WORKDIR /service

# Copy Maven wrapper and project files
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
COPY src src

# Add executable permissions to mvnw
RUN chmod +x mvnw

# Resolve dependencies and build the application
RUN ./mvnw dependency:resolve
RUN ./mvnw clean package -DskipTests

# Stage 2: Create the final image with the application
FROM eclipse-temurin:22-jre-jammy
WORKDIR /service

# Copy the packaged JAR from the build stage
COPY --from=build /service/target/financetracker-0.0.1-SNAPSHOT.jar financetracker.jar

# Command to run the JAR file
CMD ["java", "-jar", "financetracker.jar"]

## Use an appropriate base image
#FROM openjdk:11-jdk
#
## Set the working directory
#WORKDIR /app
#
## Copy the project files
#COPY . .
#
## Add executable permission to mvnw
#RUN chmod +x mvnw
#
## Build the application
#RUN ./mvnw clean install
#
## Specify the command to run your application
#CMD ["java", "-jar", "target/your-app.jar"]
