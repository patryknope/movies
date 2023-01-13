# Movie Management System
This project is a simple movie management system that allows users to perform CRUD operations on movies, search for movies, and filter movies by category.

## Getting Started
To get started with this project, you will need to clone the repository and import it into your preferred IDE.
The project contains a docker-compose.yml file, which you need to run before starting the applicaiton.
Once you have the project imported, you can run the project by running the MovieApplication class. The project will be available at http://localhost:8080.

## API Endpoints
The following API endpoints are available for this project:

`GET /movies`: Retrieve a list of all movies

`POST /movies`: Add a new movie to the system

`PUT /movies/{id}`: Update a movie in the system

`DELETE /movies/{id`}: Delete a movie from the system

`GET /movies/search`: Search for movies in the system

`PUT /movies/{id}/rating`: Rate a movie in the system

`GET /movies/categories/{category}`: Filter movies by category

## Built With
`Java` - Programming language

`Spring Boot` - Framework for building web applications

`Maven` - Dependency management

`MySQL` - Database