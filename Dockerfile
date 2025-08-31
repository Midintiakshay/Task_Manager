# ------------ Build Stage ------------
# Use Maven with JDK 17 to build the app
FROM maven:3.9.4-eclipse-temurin-17 AS build

# Set working directory
WORKDIR /app

# Copy pom.xml and download dependencies (cached for faster builds)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy the project source
COPY src ./src

# Build the application (skip tests for faster builds)
RUN mvn clean package -DskipTests


# ------------ Run Stage ------------
# Use a smaller JDK image to run the app
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy the built jar from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port (Render will override with $PORT)
EXPOSE 8080

# Run the application (bind to Render's dynamic $PORT)
CMD ["sh", "-c", "java -jar app.jar --server.port=$PORT"]
