#FROM eclipse-temurin:22-jdk-alpine
#VOLUME /tmp
#COPY target/*.jar app.jar
#ENTRYPOINT ["java","-jar","/app.jar"]
#EXPOSE 8080

FROM eclipse-temurin:17-jdk-jammy AS base
WORKDIR /service
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:resolve
COPY src src

FROM base AS build
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:17-jre-jammy
WORKDIR /service
COPY --from=build /service/target/spring-petclinic-*.jar spring-petclinic.jar
CMD ["java", "-jar", "target/financemanager.jar"]


#
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
