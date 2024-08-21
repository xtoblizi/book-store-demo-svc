# book-store-demo-svc
This is a demo project that feature web apis for managing books store and facilitating shopping cart and check-out of books added to cart

Project Structure:
This project adopts a modular domain driven structure for easy decoupling, 
separation of concerns and separation into micro-services if need be.

Internally into each domains it adopts an simplified MVC pattern to separation presentation, business-logic and persistence concerns

Persistence:
Project utilizes a repository pattern using Hibernate and JPA as the ORM tools.
For simplicity and time constraint, database migration is facilitated via hibernate and JPA;
However it is recommended to use migration tools like liquibase or flyway for extensive and well controlled db migration concerns.

H2 database is the default configured database for the app and for test for simplicity sake.

BL (Business Logic)
All business logic concerns are placed in a service which is then called from the controller (presentation layer)
For simplicity and time, a few db models are returned as response object. This will not pose much security threat in the context
of this demo service as the unique identifier is a UUID as against a IDENTITY (AUTO-INCREMENT).
However, it is advisable to use a response object (data transfer object for most or all db responses)

Build Application



