# Use an official OpenJDK runtime as a parent image
FROM openjdk:19-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the source code and pom.xml to the container
COPY src/ src/
COPY pom.xml pom.xml

# Package the application
RUN ./mvnw clean package

# Copy the jar file into the container at /app
COPY target/*.jar app.jar

# Run the jar file
ENTRYPOINT ["java","-jar","app.jar"]
