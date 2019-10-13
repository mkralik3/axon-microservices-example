FROM openjdk:8u222-jre

ADD lra-test-thorntail.jar /opt/thorntail.jar

EXPOSE 8083

CMD java ${JAVA_OPTS} -jar /opt/thorntail.jar -Djava.net.preferIPv4Stack=true -Djava.net.preferIPv4Addresses=true -Dswarm.http.port=8083