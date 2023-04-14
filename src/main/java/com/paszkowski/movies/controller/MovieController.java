package com.paszkowski.movies.controller;

import com.paszkowski.movies.model.Movie;
import com.paszkowski.movies.service.MovieService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/movies")
@Api(value = "Movie management system")
@CrossOrigin(origins = "http://localhost:3000")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }
    @GetMapping
    @ApiOperation(value = "Get all movies", response = List.class)
    public ResponseEntity<List<Movie>> getAllMovies() {
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    @PostMapping
    @ApiOperation(value = "Add a new movie", response = Movie.class)
    public ResponseEntity<Movie> addMovie(@Valid @RequestBody Movie movie) {
        return ResponseEntity.ok(movieService.addMovie(movie));
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update a movie", response = Movie.class)
    public ResponseEntity<Movie> updateMovie(@PathVariable @ApiParam(value = "Movie Id to update movie object", required = true) Long id,
                                             @Valid @RequestBody Movie movie) {
        return ResponseEntity.ok(movieService.editMovie(id, movie));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete a movie")
    public ResponseEntity<Void> deleteMovie(@PathVariable @ApiParam(value = "Movie Id to delete movie object", required = true) Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    @ApiOperation(value = "Search for movies in the system", response = List.class)
    public ResponseEntity<List<Movie>> searchMovies(@RequestParam @ApiParam(value = "Phrase to search for movies", required = true) String phrase) {
        return ResponseEntity.ok(movieService.searchMovies(phrase));
    }

    @PutMapping("/{id}/rating")
    @ApiOperation(value = "Rate a movie", response = Movie.class)
    public ResponseEntity<Movie> rateMovie(@PathVariable @ApiParam(value = "Movie Id to rate", required = true) Long id,
                                           @RequestParam @ApiParam(value = "Rating of the movie", required = true) int rating) {
        return ResponseEntity.ok(movieService.rateMovie(id, rating));
    }

    @GetMapping("/categories/{category}")
    @ApiOperation(value = "Filter movies by category", response = List.class)
    public ResponseEntity<List<Movie>> filterMoviesByCategory(@PathVariable @ApiParam(value = "Category of the movie", required = true) String category) {
        return ResponseEntity.ok(movieService.filterMoviesByCategory(category));
    }
}