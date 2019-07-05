FROM openjdk:8-jdk-alpine

COPY build/libs/movies-service.jar app.jar
EXPOSE 8080
CMD ./src/test/resources/mongodb-docker/start_docker_in_docker.sh
CMD java -jar /app.jar