FROM gradle:8.1.1-jdk17 AS build
COPY  . /home/gradle/src
WORKDIR /home/gradle/src

RUN gradle assemble
FROM amazoncorretto:17-alpine3.14
EXPOSE 8080
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/spring-boot-application.jar
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=production","/app/spring-boot-application.jar"]