package com.paszkowski.movies.controller;

import com.paszkowski.movies.model.Movie;
import com.paszkowski.movies.service.MovieService;
import com.paszkowski.movies.utils.InsertMovies;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    @Autowired
    private InsertMovies insertMovies;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    @ApiOperation(value = "Get all movies", response = Page.class)
    public ResponseEntity<Page<Movie>> getAllMovies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "title") String sort,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        PageRequest pageRequest = PageRequest.of(
                page, size,
                direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC,
                sort
        );
        return ResponseEntity.ok(movieService.getAllMovies(pageRequest));
    }

    @GetMapping("/categories/{category}")
    @ApiOperation(value = "Filter movies by category", response = Page.class)
    public ResponseEntity<Page<Movie>> filterMoviesByCategory(
            @PathVariable @ApiParam(value = "Category of the movie", required = true) String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "title") String sort,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        PageRequest pageRequest = PageRequest.of(
                page, size,
                direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC,
                sort
        );
        return ResponseEntity.ok(movieService.filterMoviesByCategory(category, pageRequest));
    }

    @PostMapping("/populate")
    @ApiOperation(value = "Populate movies")
    public ResponseEntity<String> populateMovies() {
        insertMovies.populate();
        return ResponseEntity.ok("Movies have been populated!");
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get a movie by id", response = Movie.class)
    public ResponseEntity<Movie> getMovieById(
            @PathVariable @ApiParam(value = "Id of the movie", required = true) Long id
    ) {
        return ResponseEntity.ok(movieService.getMovieById(id));
    }

    @PostMapping
    @ApiOperation(value = "Add a new movie", response = Movie.class)
    public ResponseEntity<Movie> addMovie(@Valid @RequestBody Movie movie) {
        return ResponseEntity.ok(movieService.addMovie(movie));
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update a movie", response = Movie.class)
    public ResponseEntity<Movie> updateMovie(
            @PathVariable @ApiParam(value = "Movie Id to update movie object", required = true) Long id,
            @Valid @RequestBody Movie movie
    ) {
        return ResponseEntity.ok(movieService.editMovie(id, movie));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete a movie")
    public ResponseEntity<Void> deleteMovie(
            @PathVariable @ApiParam(value = "Movie Id to delete movie object", required = true) Long id
    ) {
        movieService.deleteMovie(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/search")
    @ApiOperation(value = "Search for movies in the system", response = Page.class)
    public ResponseEntity<Page<Movie>> searchMovies(
            @RequestParam @ApiParam(value = "Phrase to search for movies", required = true) String phrase,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "title") String sort,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        PageRequest pageRequest = PageRequest.of(
                page, size,
                direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC,
                sort
        );
        return ResponseEntity.ok(movieService.searchMovies(phrase, pageRequest));
    }

}