# axon-microservices-example

In the repo are two Axon services with the common API package.

#### API
Package with the common API. The package contains all necessary API use in Axon services.

#### Hotel service
The hotel service has exposed JAX-RS endpoints and using LRA annotations. It is updated service from [hotel-service-axon](https://github.com/mkralik3/hotel-service-axon) repository. The hotel service can be joined to the LRA context in LRA quickstart. 
localhost:8082/hotel

#### Car service
The car service has exposed rest endpoint (Spring) only for testing. 
http://localhost:8085/car

GOAL: The hotel service using car service during reservation and the LRA should be propagated to the car service by axon-connector module ([lra-axon-connector](https://github.com/mkralik3/lra-axon-connector)).
 
 # How to run
```
git clone https://github.com/mkralik3/axon-microservices-example.git
cd axon-microservices-example
mvn clean install
docker run -it --rm --name my-axon-server -p 8024:8024 -p 8124:8124 axoniq/axonserver
java -Dorg.apache.tomcat.util.buf.UDecoder.ALLOW_ENCODED_SLASH=true -Dorg.apache.catalina.connector.CoyoteAdapter.ALLOW_BACKSLASH=true -jar /hotel-service-axon/target/hotel-service-1.0-SNAPSHOT.jar
java -jar /car-rent-service-axon/target/car-rent-service-1.0-SNAPSHOT.jar
```

