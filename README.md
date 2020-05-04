# Assignment
Spring Boot Microservices Application

### Prerequisite
    Java version : 8
    Maven version : 4.0.0 or above.
    Need Kafka Server installation.
	Kafka version : 2.5.0
	Zookeeper version : 3.6.1

### Services
    1.  eureka-service
        This acts as a Eureka Server.
    
    2.  auth-service
        This service acts as a gateway to the application. User authentication and authorization is done here. 
        JWT based Authentication is implemented.
        
    3.  profile-service
        This service performs User Profile operations.

### Steps to run
    1.  Start Zookeeper(port - 2181) and Kafka (port - 9092).
    2.  Kafka Topic to be used will be created automatically at application startup. Topic Name used - profile-topic
    3.  Start eureka-service
        a.  Go to eureka-service directory
        b.  Execute build command: `mvn clean install`
        c.  Execute start command: `mvn spring-boot:run`
        d.  Verify if the service is up by visiting - http://localhost:8761/
    4.  Start auth-service
        a.  Go to auth-service directory
        b.  Execute build command: `mvn clean install`
        c.  Execute start command: `mvn spring-boot:run`
        d.  Verify if the service is up by visiting `authdb` H2 Database for auth-service - http://localhost:9090/h2-console
            i.   url: jdbc:h2:mem:authdb
            ii.   username: sa
            iii.   password:
    5.  Start profile-service
        a.  Go to profile-service directory
        b.  Execute build command: `mvn clean install`
        c.  Execute start command: `mvn spring-boot:run`
        d.  Verify if the service is up by visiting `profiledb` H2 Database for profile-service - http://localhost:8888/h2-console
            i.   url: jdbc:h2:mem:prodiledb
            ii.   username: sa
            iii.   password:
    6.  After starting all the services, wait for at least 30 seconds before hitting any profile endpoint, so that services are registered to Eureka.
    7.  Login to the app
        a.  URL - http://localhost:9090/assignement/login (POST)
        b.  Request Body Format
            {
	            "username" : "omkar",
	            "password" : "12345"
            }
        c.  If successful, copy JWT token from value of `Authorization` Header from response.
            JWT Token format is similar to `Bearer eyssdfksd.eyaswerdfds.Efsdfkljlvunidd`
    8.  **Use the above JWT token for all the requests to be made through auth-service. Add a header in the request**
        a.  Header format
            i.  Key - Authorization
            ii. Value - <generated JWT Token>
    9.  Endpoints
        a.  Create Profile
            i.  HTTP POST - http://localhost:9090/assignement/profile
            ii. Request Body Format
                {
                    "address" : "abc",
	                "phoneNumber" : 123
                }
        b.  Update Profile
            i.  HTTP PUT - http://localhost:9090/assignement/profile
            ii. Request Body Format
                {
                    "address" : "abc",
	                "phoneNumber" : 123
                }
        c.  Delete Profile
            i.  HTTP DELETE - http://localhost:9090/assignement/profile
        