# offer-store-rs
JAX-RS Restful Services to create (and view) Product Offers using Jersery and H2 in-memory database.

The application uses a multi-layered architecture, "coupled" together with Spring DI
The first layer is the RESTful layer, which is implemented using the Jersey RESTful Web Services framework, it has the role of a facade and delegates the logic to the business layer.
The business layer is where the validation and any business logic (if any) happens.
The data access layer is where the communication with the persistence storage (in our case the H2 embedded database) takes place. This uses Spring's JDBCTemplate 
The .war file or exploded directory can be deployed on any web container – I used Tomcat 8.0

To build and run the Junit/Mockito tests : 
	mvn test package
	
For integration test, I used the Jersey Client and it executes requests against a running embedded Apache Tomcat with the application deployed on it. 
To run the integration test via the maven-failsafe-plugin and tomcat-maven-plugin: 
	mvn clean verify 


Product Offer can be created via the rest call, and this accepts the json conversion of the Product class:
	POST http://localhost:8080/offer-store/offers/

Product offer with productId {id} can be retrieved via rest call:
	GET http://localhost:8080/offer-store/offers/{id}



