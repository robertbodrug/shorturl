FROM openjdk:21-jdk
EXPOSE 9092
WORKDIR /app
COPY build/libs/*.jar app.jar
CMD ["java", "-jar", "app.jar"]

