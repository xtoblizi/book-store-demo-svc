# book-store-demo-svc
This is a demo project that feature web apis for managing books store and facilitating shopping cart and check-out of books added to cart

PROJECT STRUCTURE:
This project adopts a modular domain driven structure for easy decoupling, 
separation of concerns and separation into micro-services if need be.

Internally into each domain, it adopts a simplified MVC pattern to separate presentation, business-logic and persistence concerns

PERSISTENCE:
Project utilizes a repository pattern via Hibernate and JPA as the ORM tools.
For simplicity and time constraint, database migration is facilitated via hibernate and JPA;
However it is recommended to use migration tools like liquibase or flyway for extensive and well controlled db migration concerns.

H2 database is the configured database for the app, you can change to postgres , mysql or any supported hibernate db by changing the database connection config.
No native queries are used, therefore is safe and seamless to switch to a different db system.

BL (Business Logic)
All business logic concerns are placed in a service which is then called from the controller (presentation layer)
For simplicity and time, a few db models are returned as response object. This will not pose much security threat in the context
of this demo service as the unique identifier is a UUID as against a IDENTITY (AUTO-INCREMENT).
However, it is advisable to use a response object (data transfer object for most or all db responses)

SEEDED DATA: 
A set of 3 demo customers will be seeded to the database on app-start.
These customers are viable for use for your subsequent operations where a customer code is needed.
You can preview them on the customer module on the swagger documentation.

KEYS AREAS OF NOTE
1.BOOK-PRODUCT
A book product is the product side of a book item. A book can be created without price and discount information.
However for business concerns a product needs to be created off the book; This is done simply by attaching price, category (optional) to the book.
The idea of a book product is such that one book can be sold differently with different prices by a providing separate book product category.
Eg Book x, as a product Book X N10 Premium Pack with free Biro, also Same Book X N15 Regular Pack. etc.

2.CHECK-OUT AND PAYMENT IMPLEMENTATION:
After adding items (book product) to cart. Customer can choose to check-out.
At this point the customer should be availed all payment channels implemented (USSD, WEB, BANK-TRANSFER).
When the customer chooses his/her desired payment channel; customerCode, cartCode and the selected paymentChannel should be provided to the check-out 
"{baseUrl}/api/v1/check-out/{customerCode}" POST endpoint.

This endpoint will validate and process the check-out request for payment and create a transaction record for the payment attempt with a 
PENDING STATUS. Typically it awaits a call back on a webhook to update the status of the transaction.
Polymorphism and strategy patter is used to design the payment implementation.
Such that payment is the desired action but it processed in different ways per the payment gateway based on the channel. 
The different payment channel approaches are strategies which then has specific concrete implementation(s) based on the
company or provider for that channel.
This detail is contained in the checkOut domain/module.

**BUILD AND RUN APPLICATION**
To Clean, Test and Install dependency of project.
From the root folder; Run the following command below.
'mvm clean install test'

Afterwards, run the command below to package file as jar
mvm package

To run package jar file.. navigate to the target folder. From the root directory;
run command: cd /target
then execute the command below to run the jar file
java jar switch.book.store.svc-0.0.1-SNAPSHOT.jar

preview app's endpoints documentation on your browser via url:
http://localhost:8080/swagger-ui.html

Architectural Design
Reference...



