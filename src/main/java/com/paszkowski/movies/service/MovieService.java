package com.paszkowski.movies.service;

import com.paszkowski.movies.exceptions.AlreadyExistsException;
import com.paszkowski.movies.model.Movie;
import com.paszkowski.movies.repository.MovieRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MovieService implements IMovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public Page<Movie> getAllMovies(Pageable pageable) {
        return movieRepository.findAll(pageable);
    }

    @Override
    public Movie getMovieById(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Movie with id + " + id + " not found"));
    }

    @Override
    public Movie addMovie(Movie movie) {
        if (isInvalidGrade(movie.getGrade())) {
            throw new IllegalArgumentException("Invalid grade. Choose a value between 1 and 5.");
        } else if (movieRepository.existsByTitle(movie.getTitle())) {
            throw new AlreadyExistsException("Movie already exists.");
        }
        return movieRepository.save(movie);
    }

    @Override
    public Movie editMovie(Long id, Movie movie) {
        Movie existingMovie = getMovieById(id);
        existingMovie.setTitle(movie.getTitle());
        existingMovie.setYear(movie.getYear());
        existingMovie.setCategory(movie.getCategory());
        existingMovie.setDescription(movie.getDescription());
        existingMovie.setGrade(movie.getGrade());
        return movieRepository.save(existingMovie);
    }

    @Override
    public void deleteMovie(Long id) {
        movieRepository.findById(id)
                .ifPresent(movieRepository::delete);
    }

    @Override
    public Page<Movie> searchMovies(String phrase, Pageable pageable) {
        return movieRepository.findByTitleContainingIgnoreCase(phrase, pageable);
    }

    @Override
    public Movie rateMovie(Long id, int grade) {
        if (isInvalidGrade(grade)) {
            throw new IllegalArgumentException("Movies can be rated from 1 to 5.");
        }
        Movie movieToRate = getMovieById(id);
        movieToRate.setGrade(grade);
        return movieRepository.save(movieToRate);
    }

    @Override
    public Page<Movie> filterMoviesByCategory(String category, Pageable pageable) {
        return movieRepository.findByCategory(category, pageable);
    }

    private boolean isInvalidGrade(int rating) {
        return rating < 1 || rating > 5;
    }
}
