# Alpine Linux with OpenJDK JRE
FROM openjdk:11-jre-slim

# Copy war file
COPY todo.jar /todo.war

# run the app
CMD ["java", "-jar", "/todo.war"]
