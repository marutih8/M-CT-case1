# Use a minimal base image with Java 17 (adjust as needed)
FROM eclipse-temurin:17-jdk-alpine

# Set application directory
WORKDIR /app

# Copy the JAR file (adjust name if needed)
COPY target/*.jar app.jar

# Expose the application's port (default is 8080)
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
