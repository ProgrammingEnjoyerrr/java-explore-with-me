FROM amazoncorretto:11-alpine-jdk
COPY target/*.jar app.jar
EXPOSE 9091
ENTRYPOINT ["java","-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:9091","-jar","/app.jar"]