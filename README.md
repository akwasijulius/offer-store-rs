# offer-store-rs
JAX-RS Restful Services to CRUD operations on Products using Jersery, Spring and H2 in-memory database.

The application uses a multi-layered architecture, "coupled" together with Spring DI. <BR>
The first layer is the RESTful layer, which is implemented using the Jersey RESTful Web Services framework, it has the role of a facade and delegates the logic to the business layer. <BR>
The business layer is where the validation and any business logic (if any) happens. <BR>
The data access layer is where the communication with the persistence storage (in our case the H2 embedded database) takes place. This uses Spring's JDBCTemplate. <BR>
The .war file or exploded directory can be deployed on any web container – I used Tomcat 8.0 <BR>

To build and run the Junit/Mockito tests : <BR>
<b>mvn test package</b>
	
For integration test, I used the Jersey Client and it executes requests against a running embedded Apache Tomcat with the application deployed on it. <BR> 
To run the integration test via the maven-failsafe-plugin and tomcat-maven-plugin: <BR>
<b>mvn clean verify</b> 
<BR>
<BR>

Product Offer can be created via the rest call, and this accepts the json conversion of the Product class: <BR>
	POST http://localhost:8080/offer-store/offers/

Product offer with productId {id} can be retrieved via rest call: <BR> GET http://localhost:8080/offer-store/offers/{id}



