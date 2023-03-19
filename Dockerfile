FROM openjdk:11-jdk-slim
WORKDIR /app
COPY src ./src
COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle
RUN ./gradlew clean
RUN ./gradlew build
EXPOSE 8080

CMD ["java", "-jar", "/app/build/libs/pablo-escobar-avla.jar"]