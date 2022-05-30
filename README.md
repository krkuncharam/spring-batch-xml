# SPRING BATCH USING XML CONFIGURATION

Reads LDIF file to parse the data and save it to MySQL database

# CREATE TABLE QUERY:

CREATE TABLE USERS (USER_ID INT NOT NULL AUTO_INCREMENT, 
        USER_NAME VARCHAR(16) NOT NULL, USER_FIRST_NAME VARCHAR(40) NOT NULL, 
		USER_LAST_NAME VARCHAR(40) NOT NULL, USER_EMAIL CHAR(100) NOT NULL, 
        USER_LAST_LOGIN_TS TIMESTAMP NOT NULL, PRIMARY KEY ( USER_ID ));
        
# EXECUTION (IDE):
Run as "Java Application" -> "LaunchJob.java" through IDE.

# EXECUTION (CLI):
Navigate to the project directory

>mvn clean install

After build process is complete

>java -jar spring-batch-1.0.jar
