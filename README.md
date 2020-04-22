# Short Distance
## Description
This Short Distance project is a Spring Boot maven project which finds the shortest route
from a source planet - Earth - to a destination planet. It uses JPA Hibernate for 
persistence and Tomcat as the web server.  
It first loads planet and route input data from CSV files into Derby embedded database.
CRUD operations for both planets and routes are exposed as REST webservice. The distance 
algorithm implementation to find the shortest route is also exposed as both REST and SOAP 
webservice to be used by external tools like SoapUI. The WSDL url is
http://localhost:8082/ws/distance.wsdl.  

## Development server
### Prerequisites and assumptions
JAVA 8  
Maven 3.0+  

## Build and Run
* Clone the project into a new folder of your choice  
git clone https://github.com/ehabtema/shortDistance.git
* 'mvn spring-boot:run' - to build and run the project

## Unit Tests
There are unit tests to test the different components of the project
which can be found under 'ReadCsvIntoDbTest', 'ShortDistTest' 

## Testing SOAP service  
The distance algorithm's findShortestRoute method is exposed as a SOAP
service. The WSDL for the service is give above and can tested using
external tools like SoapUI. To limit who can access the service, the 
SOAP service is secured with basic username and password authentication.  
username: admin  
password: admin  
Basic authorization need to be configured on SoapUI before sending the request.
