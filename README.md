# TODO List API

Welcome to the TODO List REST API, built with Spring Boot! 
This application offers endpoints to efficiently manage your TODO tasks.

## Overview
This Spring Boot application functions as a backend service for managing TODO lists. 
It provides RESTful endpoints for performing CRUD (Create, Read, Update, Delete) operations on tasks, enabling users to easily manage their task lists.

### Endpoints:
- **POST /users**: Create new user
- **POST /todos/{username}**: Create new TODO item
- **GET /todos/{username}**: Retrieve all TODO items of a user
- **PUT /todos/{username}/{title}**: Update specific task of a user
- **DELETE /todos/{username}/{title}**: Delete specific task of a user

### Todo model:
- **id**: A unique identifier of a task
- **title**: A name of a task
- **description**: A description of a task user wants to complete
- **user**: A user this task belongs to

### User model:
- **id**: A unique identifier of a user
- **username**: A username of a user
- **firstName**: A first name of a user
- **lastName**: A last name of a user
- **todos**: A list of todo items user has created

## How to run this application:

### Prerequisites:
- Java 17 or higher installed.
- Maven installed.
- MySQL installed and running (you can use a local instance or a cloud-based service).

### Steps
1. Clone this repository:

```shell
git clone https://github.com/Mariam-Katamashvili/Todo-API.git
```
2. Navigate to the project directory:

```sh
cd Todo-API
```
3. Set up the database:

Create a database in your SQL server.
Update application.properties with your database credentials and URL.


4. Build the project:

```sh
mvn clean install
```

5. Run the application:

```sh
java -jar target/Todo-API-1.0.jar
```

The application will start running at `http://localhost:8080`.

## How to send requests:

You can use tools like Postman or any HTTP client
library to send requests to the API endpoints.

## Experience while Developing/Learning with ChatGPT:
  - It was quite easy to complete this task using AI. ChatGPT had all answers that I had
and could provide code snippets.
- The task took me about 5 hours in total to complete.
- Code was not always ready to run, since there were times when
  GPT is hallucinating and provides code variables or DTOs that don't exist.
Sometimes GPT also seems to ignore what the task is the does whatever it wants. But overall it is very helpful.
- Challenge of this task was that there was very little instructions given and I had to decide 
which methods to add in the controller and what kind of DTOs to use.
- The prompt that I used the most `Help me do the following task
  within this method """Code snippet""""`
