FROM openjdk:17-jdk-alpine
EXPOSE 9092
WORKDIR /app
COPY build/libs/*.jar app.jar
CMD ["java", "-jar", "app.jar"]

