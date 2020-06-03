# Getting Started

### Reference Documentation

## Tests:
  - Tests using JUnit were created to met all the requirements and outputs provided.

## Requirements:

- Implemented and tested using Java 11

- Tests require JUnit.

- Project dependencies and compiling managed by Maven.


## Installing and running the application:

Compile: mvn compile

Test: mvn test

Packaging: mvn package, compiled jar in target/ folder

Run: "cd target" | "java -jar bmp-assessment-0.0.1-SNAPSHOT.jar"

commands:
curl --location --request POST 'http://localhost:8080/counter-api/search' \
--header 'Authorization: Basic b3B0dXM6Y2FuZGlkYXRlcw==' \
--header 'Content-Type: application/json' \
--data-raw '{"searchText":["Duis", "Sed", "Donec","Augue","Pellentesque", "123"]}'