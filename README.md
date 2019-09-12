# ticket_booking_app

Application for booking tickets at cinema. Operations are exposed as REST services.  
Application is written in Java using: Java 12, Spring Boot 2, Hibernate, Mysql 5.7, jUnit 5.  

### In order to run application use shell script.

1. Mysql server have to be running and Maven should be installed.  

2. Run script  
  `$ ./ticket_booking_app/run_script`
  
The script:  
  * creates database `cinema_db`  
  * creates user `cinema_user`  
  * build application using Maven  
  * creates system user `ticket_app_user`  
  * creates and run service `ticket_app`  
  
3. Service can be stopped by:
  `$ service ticket_app stop`
  
### Runnning use case script. 
It use "jq" to display json responses. Jq can be installed by:
  `$ apt-get jq`
  
1. Run use case script  
  `$ ./ticket_booking_app/use_case_script`  
  
2. Path to example output:  
`/ticket_booking_app/use_case_output.txt`  
