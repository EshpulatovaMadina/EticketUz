FROM openjdk:17

CMD ["./gradlew", "clean", "bootJar"]

COPY build/libs/EticketUz-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app.jar"]
