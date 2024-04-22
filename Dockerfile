FROM openjdk:21-jdk
EXPOSE 8080
WORKDIR /app
COPY build/libs/*.jar app.jar
CMD ["java", "-jar", "app.jar"]

