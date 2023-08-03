# Use the official OpenJDK image as a parent image
FROM openjdk:17-jdk-slim-buster

# Create and set the working directory
RUN mkdir /app
WORKDIR /app

# Copy the Gradle files
COPY build.gradle .
COPY gradle gradle
COPY settings.gradle .
COPY gradlew .

# Change permissions of Gradle wrapper
RUN chmod +x gradlew

# Copy the source code
COPY src src

# Build the application
RUN ./gradlew clean build

# Expose the port
EXPOSE 9002

# Start the application
CMD ["java", "-jar", "/app/build/libs/room-service-0.0.1-SNAPSHOT.jar"]
