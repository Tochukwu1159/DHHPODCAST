# Use Maven image to build the project
FROM maven:3.8.5-openjdk-17 AS build

# Copy the entire project (including pom.xml) to the container
COPY . /usr/src/app

# Set the working directory to where the pom.xml is located
WORKDIR /usr/src/app

# Run Maven to build the project, skipping tests
RUN mvn clean package -DskipTests

# Use a smaller OpenJDK image to run the application
FROM openjdk:17.0.1-jdk-slim

# Copy the built jar file from the build stage to the runtime image
COPY --from=build /usr/src/app/target/dhhpodcast-0.0.1-SNAPSHOT.jar /app/app.jar

# Expose the application port
EXPOSE 8081

# Run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
