FROM openjdk:11-jre
#exposing the following port to outside of docker
EXPOSE :8080
ADD target/boot-example-docker.jar boot-example-docker.jar
ENTRYPOINT ["java", "-jar", "/boot-example-docker.jar"]