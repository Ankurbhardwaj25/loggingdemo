# Use Java 17 runtime
FROM eclipse-temurin:17-jre

# Set working directory
WORKDIR /app

# Copy built JAR
COPY target/*.jar app.jar

# Expose port (matches Spring Boot application.properties)
EXPOSE 8081

# Run JAR
ENTRYPOINT ["java","-jar","app.jar"]
