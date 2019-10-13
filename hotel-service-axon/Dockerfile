FROM openjdk:8u222-jre

VOLUME /tmp

ARG JAR_FILE=target/hotel-service-1.0-SNAPSHOT.jar

ADD ${JAR_FILE} hotel-service.jar

EXPOSE 8082

CMD java ${JAVA_OPTS} -jar /hotel-service.jar -Djava.security.egd=file:/dev/./urandom