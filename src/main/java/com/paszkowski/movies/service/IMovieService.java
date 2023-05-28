package com.paszkowski.movies.service;

import com.paszkowski.movies.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IMovieService {
    Page<Movie> getAllMovies(Pageable pageable);
    Page<Movie> getAllMoviesByUser(String email, Pageable pageable);
    Movie getMovieById(Long id);
    Movie addMovie(Movie movie);
    Movie editMovie(Long id, Movie movie);
    void deleteMovie(Long id);
    void deleteAllMovies();
    Page<Movie> searchMovies(String phrase, Pageable pageable);
    Movie rateMovie(Long id, int rating);
    Page<Movie> filterMoviesByCategory(String category, Pageable pageable);
}
