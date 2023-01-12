package com.paszkowski.movies.service;

import com.paszkowski.movies.model.Category;
import com.paszkowski.movies.model.Movie;

import java.util.List;

public interface IMovieService {
    List<Movie> getAllMovies();
    Movie getMovieById(Long id);
    Movie addMovie(Movie movie);
    Movie editMovie(Long id, Movie movie);
    void deleteMovie(Long id);
    List<Movie> searchMovies(String phrase);
    Movie rateMovie(Long id, int rating);
    List<Movie> filterMoviesByCategory(Category category);

}
