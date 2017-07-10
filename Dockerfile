FROM openjdk:latest
MAINTAINER Piotr Zlotowski
RUN mkdir -p /var/til-app
COPY target/til-0.0.1-SNAPSHOT.jar /var/til-app/
EXPOSE 8080
CMD ["java", "-jar", "/var/til-app/til-0.0.1-SNAPSHOT.jar"]

