FROM adoptopenjdk/openjdk11:alpine-jre
WORKDIR /app
COPY build/libs/*.jar app.jar
CMD ["java", "-jar", "app.jar"]
