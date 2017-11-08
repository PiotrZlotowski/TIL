FROM openjdk:latest
MAINTAINER Piotr Zlotowski
RUN mkdir -p /var/til-app
COPY target/til-0.0.1-SNAPSHOT.jar /var/til-app/
EXPOSE 8080
CMD ["java","-Djava.rmi.server.hostname=172.30.99.177",  "-Dcom.sun.management.jmxremote", "-Dcom.sun.management.jmxremote.port=9010", "-Dcom.sun.management.jmxremote.rmi.port=9010", "-Dcom.sun.management.jmxremote.local.only=false", "-Dcom.sun.management.jmxremote.authenticate=false", "-Dcom.sun.management.jmxremote.ssl=false", "-jar", "/var/til-app/til-0.0.1-SNAPSHOT.jar"]
