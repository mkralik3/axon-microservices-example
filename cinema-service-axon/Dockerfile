FROM openjdk:8u222-jre

VOLUME /tmp

ARG JAR_FILE=target/cinema-service-1.0-SNAPSHOT.jar

ADD ${JAR_FILE} cinema-service.jar

EXPOSE 8086

CMD java ${JAVA_OPTS} -jar /cinema-service.jar -Djava.security.egd=file:/dev/./urandom