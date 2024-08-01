
Sample Endpoints for RESTful CRUD Order API by Kyle Jang

This document provides instructions on how to run an application and sample requests for each endpoint of the RESTful CRUD Order API. These examples use CURL commands to demonstrate how to interact with the API.

To Run an Application:
Please input following Program Arguments:

Spring Profile: -Dspring.profiles.active=prod 
Logging Level:  -Dlogging.level.org.springframework=INFO
JVM Memory Settings: -Xms512m 
Garbage Collection: -XX:+UseG1GC

All-Together: -Dspring.profiles.active=prod  -Dlogging.level.org.springframework=INFO -Xms512m -XX:+UseG1GC

Sepearte Documentation Using Swagger API: http://localhost:8080/swagger-ui/index.html 

Base URL: http://localhost:8080/v1/order
/v1: Indicates version 1
/order: Base URL for performing CRUD operations on an object Order

API EndPoints Descriptions:

1. Create an Order
Endpoint: POST /create
Description: Creates a new order.
Request Parameter: ID is automatically generated, and none of the Order fields can NOT be null (or missing).

a) Use java.time.LocalDate format (ex: "2024-07-10", "2024-07-10T10:00:00", etc…) 
b) Amount has to be positive (greater than 0), and any decimals bigger points longer than 2 gets rounded up to 2 decimal digits internally
c) Currency_code has to be exactly 3 characters, and only one of 185 ISO-4217 currency codes
d) TransactionType has to be either “Sale” or “Refund”. [Case Sensitive]

curl -X POST http://localhost:8080/v1/order/create \
-H "Content-Type: application/json" \
-d '{
  "date": "2024-07-10T10:00:00",
  "amount": 100.00,
  "currencyCode": "USD",
  "transactionType": "Sale"
}'


2. Get Order by ID
Endpoint: GET /{id}/get
Description: Retrieves an order by its ID.
Request Parameter: {id} has to be a single positive-numeric number.  

curl -X GET http://localhost:8080/v1/order/1/get


3. Update an Order by ID
Endpoint:  PUT /{id}/update
Description: Updates an existing order by its ID.
Request Parameters:  {id} has to be a single positive-numeric number.

Payload description: None of the fields can NOT be null (or missing).
a) Use java.time.LocalDate format (ex: "2024-07-10", "2024-07-10T10:00:00", etc…) 
b) Amount has to be positive (greater than 0), and any decimals bigger points longer than 2 gets rounded up to 2 decimal digits internally
c) Currency_code has to be exactly 3 characters, and only one of 185 ISO-4217 currency codes
d) TransactionType has to be either “Sale” or “Refund”. [Case Sensitive]

curl -X PUT http://localhost:8080/v1/order/1/update \
-H "Content-Type: application/json" \
-d '{
  "date": "2024-07-10T10:00:00",
  "amount": 150.00,
  "currencyCode": "USD",
  "transactionType": "Sale"
}'


4. Delete an Order by ID
Endpoint: DELETE /{id}/delete
Description: Deletes an order by its ID.
Request Parameter:  ID is automatically generated 

 curl -X DELETE http://localhost:8080/v1/order/1/delete


5. List All Orders
Endpoint: GET /listAll
Description: Lists all orders with optional pagination, sorting, and search criteria.
Request Parameter: N/A 

curl -X GET http://localhost:8080/v1/order/list


6. Health Check  http://localhost:8080/actuator/health
Endpoint: http://localhost:8080/actuator/health → No_Base_URL
Description: Returns the status of the application, disk space, ping, and Database (H2)
Request Parameter: N/A

curl http://localhost:8080/actuator/health


7. If I had more time I would develop followings:
--> Find Orders by using different parameters other than Order_ID. 
For Example: Date, Currecny_Code, and return the orders either in an ASC. or DESC. order of Amount/Price or TimeStamp
--> Implemente monitoring, and alerting system using Spring Boot Actuator and Prometheus 
--> Have option to create/update multiple orders
--> Implemente authentication systems in Spring using Spring Security such as HTTP Basic, form-based login, OAuth2, and JWT.
--> More complex Implementation of custom exception using HttpServletRequest
