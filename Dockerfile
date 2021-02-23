FROM openjdk:11-jre-slim
VOLUME /tmp
ADD target/kafkaMail-1.0.202008.jar target/app.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prodkafka","target/app.jar"]

