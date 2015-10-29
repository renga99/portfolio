# CODEZERO-visual agent builder - Spring MVC ,AngularJs based application #


### Installation dependencies ###

The following dependencies are necessary: 

 - Java 8
 - Node 0.12 or higher
 - bower
 - maven 3

### Installing frontend dependencies ###

After cloning the repository, the following command installs the Javascript dependencies:

    bower install

### Building and starting the server ###

To build the backend and start the server, run the following command on the root folder of the repository:

    mvn clean install tomcat7:run-war -Dspring.profiles.active=test

The spring test profile will activate an in-memory database. After the server starts, the application is accessible at the following URL:

    http://localhost:8080/

    username: admin / password: admin

### Frontend Overview ###


### Backend Overview ###


#### Backend Security ####


#### REST API ####


### Testing code coverage ###



## Installation instructions

Clone this repository, install nodejs and bower and on the root of the repository run this command:

    bower install
    
Then run one of the maven commands bellow. 

### How to run the project against a PostgreSQL database ###

This command starts the application with a local postgresql database:

    mvn clean install tomcat7:run-war -Dspring.profiles.active=development

### How to run the project in HTTPS-only mode ###

The application can be started in HTTPS only mode by using the flag httpsOnly=true. This works in both modes, this is an example of how to start the application in test mode and HTTPS only:

    mvn clean install tomcat7:run-war -Dspring.profiles.active=test -DhttpsOnly=true

The project can be accessed via this URL:

    https://localhost:8443/
    
A warning message is displayed because the test certificate is not accepted by the browser, by accepting the certificate the login page is then displayed.
