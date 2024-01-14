FROM openjdk:17
WORKDIR /app
COPY /target/DelesBlog-0.0.1-SNAPSHOT.jar /app/DelesBlog-app.jar
EXPOSE 8082
CMD ["java", "-jar", "DelesBlog-app.jar"]