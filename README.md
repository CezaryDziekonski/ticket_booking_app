# ticket_booking_app

Application for booking tickets at cinema. Operations are exposed as REST services.  
Application is written in Java using: Java 12, Spring Boot 2, Hibernate, Mysql 5.7, jUnit 5.  

### In order to build and run application use Docker.

Navigate to the repository folder and run:  
`$ docker-compose up`  
It runs two containers on single host:  
- spring boot application
- mysql server

To stop all services use:  
`$docker-compose down`  

### Runnning use case script. 
It use "jq" to display json responses. Jq can be installed by:  
  `$ apt-get jq`  
  
1. Run use case script    
  `$ ./ticket_booking_app/use_case_script`    
  
2. Path to example output:    
`/ticket_booking_app/use_case_output.txt`    
