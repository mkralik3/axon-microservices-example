FROM openjdk:8u222-jre

VOLUME /tmp

ARG JAR_FILE=target/vehicle-rent-service-1.0.jar

ADD ${JAR_FILE} vehicle-service.jar

EXPOSE 8085

CMD java ${JAVA_OPTS} -jar /vehicle-service.jar -Djava.security.egd=file:/dev/./urandom