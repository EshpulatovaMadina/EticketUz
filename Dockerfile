FROM openjdk:17
EXPOSE 3000
ARG JAR_FILE=build/libs/EticketUz-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} eticket-uz
ENTRYPOINT ["java","-jar", "eticket-uz"]