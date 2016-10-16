# offer-store-rs
JAX-RS Restful Services to create and view Product Offers using Jersery and H2 in-memory database.


To build and run the Junit/Mokito tests : 
	mvn test package
To run the integration test via the maven-failsafe-plugin and tomcat7-maven-plugin: 
	mvn clean verify 


Product Offer can be created via the rest call:
	POST http://localhost:8080/offer-store/offers/

Product offer with productId 101 can be retrieved via rest call:
	GET http://localhost:8080/offer-store/offers/101


