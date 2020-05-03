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
        
    3.  profile-service
        This service performs User Profile operations.

### Steps to run
    1.  Start Zookeeper(port - 2181) and Kafka (port - 9092).
    2.  Kafka Topic to be used will be created automatically at application startup. Topic Name used - profile-topic
    3.  Start eureka-service
        *  Go to eureka-service directory
        *  Execute build command: `mvn clean install`
        *  Execute start command: `mvn spring-boot:run`
        *  Verify if the service is up by visiting - http://localhost:8761/
    4.  Start auth-service
        *  Go to auth-service directory
        *  Execute build command: `mvn clean install`
        *  Execute start command: `mvn spring-boot:run`
        *  Verify if the service is up by visiting `authdb` H2 Database for auth-service - http://localhost:9090/h2-console
    5.  Start profile-service
        *  Go to profile-service directory
        *  Execute build command: `mvn clean install`
        *  Execute start command: `mvn spring-boot:run`
        *  Verify if the service is up by visiting `profiledb` H2 Database for profile-service - http://localhost:8888/h2-console
    6.  Login to the app
        *  URL - http://localhost:9090/assignement/login (POST)
        *  Request Body Format
            `{
	            "username" : "omkar",
	            "password" : "12345"
            }`