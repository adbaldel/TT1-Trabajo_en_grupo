FROM eclipse-temurin:21-jre
WORKDIR ./

# Copy the generated JAR from the build stage
COPY ./target/simserver-1.0-SNAPSHOT.jar simserver.jar

# Expose the port the Jersey server runs on
EXPOSE 8081

# Run the jar file
ENTRYPOINT ["java", "-jar", "simserver.jar"]