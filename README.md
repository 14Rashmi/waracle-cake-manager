# waracle-cake-manager

## Running
```
git clone https://github.com/14Rashmi/waracle-cake-manager.git
cd waracle-cake-manager
mvn spring-boot:run
```
## Basic Operations
```
+ to see an Human representaion of all cakes hit the root URL http://localhost:8080. A new cake can also be added to the system from here.
+ to see a JSON view of all cake resources hit the URL http://localhost:8080/cakes
+ to add a cake make a JSON POST request to the URL http://localhost:8080/cakes
```
## Authentication
```
+ The Human representation view http://localhost:8080 has been authenticated using github credentials
+ The GET and POST to "/cakes" can be accessed without any Authentication - to demonstrate that Authentication can be implemented for selected URLs.
```
## Continuous Integration
```
+Continuous Integration has been implemented using CircleCI.
+Can be accessed using the link: https://app.circleci.com/pipelines/github/14Rashmi/waracle-cake-manager
```
