# Step 1: Use OpenJDK image
FROM openjdk:17-jdk-slim

# Step 2: Add a volume to store temporary files
VOLUME /tmp

# Step 3: Set working directory
WORKDIR /app

# Step 4: Copy built jar file into container
COPY target/auth-service-0.0.1-SNAPSHOT.jar app.jar

# Step 5: Run the jar
ENTRYPOINT ["java", "-jar", "app.jar"]
