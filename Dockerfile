# Use OpenJDK 17 as base image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy Maven wrapper and pom.xml first (for dependency caching)
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Download dependencies (offline build cache)
RUN ./mvnw dependency:go-offline -B

# Copy the rest of the project
COPY src ./src

# Build the application
RUN ./mvnw clean package -DskipTests

# Expose Render's port (Render sets $PORT automatically)
EXPOSE 8080

# Run the application
CMD ["sh", "-c", "java -jar target/*.jar --server.port=$PORT"]
