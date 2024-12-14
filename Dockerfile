# Use an official OpenJDK runtime as a parent image
FROM openjdk:21-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the Maven wrapper and pom.xml to the container
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Download the project dependencies
RUN ./mvnw dependency:go-offline

# Copy the project source code to the container
COPY src ./src

# Compile the project
RUN ./mvnw package -DskipTests

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "target/*.jar"]