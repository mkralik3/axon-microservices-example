# Axon LRA microservices example

This project demonstrating usage of [Axon LRA connector](https://github.com/mkralik3/lra-axon-connector) in Axon based services. Thanks to that, the Axon Aggregates can join the active LRA context and they can be managed by LRA coordinator. 
![GitHub Logo](./img/axonLraDiagramFullQuickstart.png)

### USE CASE
The quickstart consists of five services that represent reservation systems. 
It represents a business process that books a trip that consists of flight and hotel reservations. The hotel reservation is extended with making hotel's car reservation and a cinema ticket reservation.
The booking trip service is the leading service where the LRA is started.

#### Project structure
![GitHub Logo](./img/quickstartArch.png)

#### Sequence diagram
![GitHub Logo](./img/quckstartFullSequence.png)

##### API
Package with the common API. The package contains all necessary Commands, Events and Queries use in Axon services. For simplified, the classes are provided in one Kotlin file.

##### Hotel service
The hotel service has exposed JAX-RS endpoints and using LRA annotations. It is updated service from [hotel-service-axon](https://github.com/mkralik3/hotel-service-axon) repository. The hotel service can be joined to the LRA context in LRA quickstart by `narayana-lra`.

Endpoints:
* http://localhost:8085/hotel

##### Vehicle rent service
The vehicle rent service has two aggregates, Car and Van. The service uses Spring REST. 

Endpoints:
* http://localhost:8085/car
* http://localhost:8085/van
* http://localhost:8085/axonLra/aggregateInfo (Axon LRA connector)

##### Cinema ticket service
The cinema ticket service has only one aggregate and doesn't have any explicit REST endpoints. (only from Axon LRA connector)

Endpoints:
* http://localhost:8086/axonLra/aggregateInfo (Axon LRA connector)

# How to run

###### Workaround for JBTM-3161
```
git clone https://github.com/jbosstm/narayana.git
cd narayana/rts/lra
git checkout 5.9.8.Final
sed -i 's/LRAParticipantRegistry()/public LRAParticipantRegistry()/g' ./lra-proxy/api/src/main/java/io/narayana/lra/client/internal/proxy/nonjaxrs/LRAParticipantRegistry.java
mvn clean install -DskipTest
```
##### 1. Build Axon LRA connector
The services uses [Axon LRA connector](https://github.com/mkralik3/lra-axon-connector) to join into LRA. First, it is needs to add it to local repository.
```
git clone https://github.com/mkralik3/lra-axon-connector.git
cd lra-axon-connector
mvn clean install
```

##### 2. Build example microservices

 ```
 git clone https://github.com/mkralik3/axon-microservices-example.git
 cd axon-microservices-example
 mvn clean install
 ```
##### 3a. Set up via Docker compose
First, it is needed to copy generated Thorntail services (jar files) from [Narayana quickstart](https://github.com/jbosstm/quickstart/tree/master/rts/lra) to the particular folders `lraResources/flight` and `lraResources/trip`.
```
cp <NARAYANA_QUICKSTARTS>flight-service/target/lra-test-thorntail.jar <microservices-parent>/lraResources/flight/
cp <NARAYANA_QUICKSTARTS>trip-controller/target/lra-test-thorntail.jar <microservices-parent>/lraResources/trip/
```

After that, run the command:
```
docker-compose up
```
During starting, the errors can appear in the log. It is due to the fact, that Axon Server is not ready yet but Axon services want to connect to it. When the Axon services are successfully connected to the Axon server, you will see `AxonServerEventStore  : open stream: 0
` in the log.

```
docker logs -f lra-coordinator
docker logs -f flight
docker logs -f trip
docker logs -f axon-server
docker logs -f hotel
docker logs -f vehlicle
docker logs -f cinema
```
#### 3b. Set up manually
```
docker run -it --rm --name my-axon-server -p 8024:8024 -p 8124:8124 axoniq/axonserver:4.2
java -jar <lraCoordinator> -Dswarm.http.port=8080
java -jar lraResources/flight/lra-test-thorntail.jar -Dswarm.http.port=8083 -Dlra.http.port=8080
java -jar lraResources/trip/lra-test-thorntail.jar -Dswarm.http.port=8084 -Dlra.http.port=8080

java -jar cinema-service-axon/target/cinema-service-1.0-SNAPSHOT.jar -Dlra.http.port=8080
java -jar vehicle-rent-service-axon/target/vehicle-rent-service-1.0-SNAPSHOT.jar -Dlra.http.port=8080
java -Dorg.apache.tomcat.util.buf.UDecoder.ALLOW_ENCODED_SLASH=true -Dorg.apache.tomcat.util.buf.UDecoder.ALLOW_ENCODED_SLASH=true" -jar hotel-service-axon/target/hotel-service-1.0-SNAPSHOT.jar  -Dlra.http.port=8080 
```
#### 4. Client Example
```
mvn -f trip-client/pom.xml exec:java -Dservice.http.host="localhost" -Dservice.http.port=8084
```
[TBD]
