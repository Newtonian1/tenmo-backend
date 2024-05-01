This project is a Java based RESTful API for a money transferring application such as Venmo. 
The server code is comprised of three layers: the controller package, the business package, and the DAO package. 
First, the controller layer provides endpoints for a client application to send HTTP requests to. This layer is responsible for authorization and passing request data to the business layer.
Next, the business layer implements business logic to determine what database operations, if any, should be performed for any valid requests. If the database needs to be accessed, the business layer calls the DAO layer.
Finally, the DAO layer is responsible for querying the SQL database using prepared statements to protect against SQL injection attacks. This layer also helps enforce ACID compliance on the data in the database.

Note on testing:
Testing classes utilize the JUnit framework for automated unit testing.
Integration testing was performed using Postman to simulate client requests.
