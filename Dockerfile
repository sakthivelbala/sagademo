# Use a base image with Java 23 installed
FROM openjdk:23-jdk-slim

# Install supervisor
RUN apt-get update && apt-get install -y supervisor && apt-get clean

# Set the working directory
WORKDIR /app

# Copy the entire release folder into the Docker image
COPY release/ ./release/

# Copy the supervisor configuration file
COPY supervisord.conf /etc/supervisor/conf.d/supervisord.conf

# Make the run-jars.sh script executable
RUN chmod +x ./release/run.sh

EXPOSE 8080

# Command to run supervisord
CMD ["supervisord", "-c", "/etc/supervisor/conf.d/supervisord.conf"]
