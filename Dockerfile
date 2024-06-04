FROM openjdk:17-jdk
VOLUME /tmp
ARG JAR_FILE
COPY target/Sphere-0.0.1.jar sphere-0.0.1.jar
ENTRYPOINT ["java","-jar","/sphere-0.0.1.jar"]