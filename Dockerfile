FROM openjdk:11
COPY /target/rent-service.jar /app/rent-service.jar
EXPOSE 8080/tcp
CMD java -jar /app/rent-service.jar
