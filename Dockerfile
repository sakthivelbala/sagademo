# Use a base image with Java 23 installed
FROM openjdk:23-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the entire release folder into the Docker image
COPY release/ ./release/

# Make the run-jars.sh script executable
RUN chmod +x ./release/run.sh

EXPOSE 8080

# Command to run the script
CMD ["sh", "./release/run.sh"]