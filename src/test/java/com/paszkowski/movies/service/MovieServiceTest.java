package com.paszkowski.movies.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.paszkowski.movies.exceptions.AlreadyExistsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.paszkowski.movies.model.Movie;
import com.paszkowski.movies.repository.MovieRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieService movieService;

    @Test
    void addMovie_shouldReturnSavedMovie() {
        // given
        Movie movieToAdd = new Movie(null, "Movie 1", 2020, "ACTION", "Description 1", 5);
        Movie expectedMovie = new Movie(1L, "Movie 1", 2020, "ACTION", "Description 1", 5);
        when(movieRepository.save(movieToAdd)).thenReturn(expectedMovie);

        // when
        Movie actualMovie = movieService.addMovie(movieToAdd);

        // then
        assertEquals(expectedMovie, actualMovie);
    }
    @Test
    public void givenExistingMovie_whenAddMovie_thenThrowAlreadyExistsException() {
        //given
        Movie movie = new Movie(1L, "Matrix", 2020, "COMEDY", "Description", 3);
        when(movieRepository.existsByTitle(movie.getTitle())).thenReturn(true);

        //then
        assertThrows(AlreadyExistsException.class, () -> movieService.addMovie(movie));
        verify(movieRepository, times(0)).save(movie);
    }

    @Test
    void whenAddMovieWithInvalidGrade_thenThrowIllegalArgumentException() {
        //given
        Movie movieWithInvalidGrade = new Movie(null, "Matrix", 2020, "ACTION", "Test Description", 0);

        //then
        assertThrows(IllegalArgumentException.class, () -> movieService.addMovie(movieWithInvalidGrade));
    }

    @Test
    void getAllMovies_shouldReturnAllMovies() {
        // given
        List<Movie> expectedMovies = Arrays.asList(
                new Movie(1L, "Matrix 1", 2020, "ACTION", "Description 1", 5),
                new Movie(2L, "Matrix 2", 2019, "COMEDY", "Description 2", 4)
        );
        Page<Movie> expectedPage = new PageImpl<>(expectedMovies);
        when(movieRepository.findAll(any(Pageable.class))).thenReturn(expectedPage);

        // when
        Page<Movie> actualPage = movieService.getAllMovies(PageRequest.of(0, 10));

        // then
        assertEquals(expectedPage, actualPage);
    }

    @Test
    void getMovieById_shouldReturnMovie() {
        // given
        long movieId = 1L;
        Movie expectedMovie = new Movie(movieId, "Matrix", 2020, "ACTION", "Description 1", 5);
        when(movieRepository.findById(movieId)).thenReturn(Optional.of(expectedMovie));

        // when
        Movie actualMovie = movieService.getMovieById(movieId);

        // then
        assertEquals(expectedMovie, actualMovie);
    }

    @Test
    void getMovieById_shouldThrowIllegalArgumentException() {
        // given
        long movieId = 100L;

        // when
        when(movieRepository.findById(movieId)).thenReturn(Optional.empty());

        // then
        assertThrows(IllegalArgumentException.class, () -> movieService.getMovieById(movieId));
    }

    @Test
    void editMovie_shouldReturnEditedMovie() {
        // given
        long movieId = 1L;
        Movie movieToEdit = new Movie(movieId, "New Matrix", 2022, "DRAMA", "New Description", 4);
        Movie existingMovie = new Movie(movieId, "Old Matrix", 2020, "ACTION", "Old Description", 5);
        when(movieRepository.findById(movieId)).thenReturn(Optional.of(existingMovie));
        when(movieRepository.save(existingMovie)).thenReturn(movieToEdit);

        // when
        Movie actualMovie = movieService.editMovie(movieId, movieToEdit);

        // then
        assertEquals("New Matrix", actualMovie.getTitle());
        assertEquals(2022, actualMovie.getYear());
        assertEquals("DRAMA", actualMovie.getCategory());
        assertEquals("New Description", actualMovie.getDescription());
        assertEquals(4, actualMovie.getGrade());
    }

    @Test
    public void deleteMovie_validId_movieDeleted() {
        // given
        long movieId = 1L;
        Movie movie = new Movie(movieId, "Matrix", 2020, "ACTION", "Description", 0);
        movieRepository.save(movie);


        // when
        movieService.deleteMovie(movieId);

        // then
        Optional<Movie> deletedMovie = movieRepository.findById(movieId);
        assertFalse(deletedMovie.isPresent());
    }
    @Test
    public void deleteMovie_invalidId_movieNotDeleted() {
        // given
        long movieId = -1;

        // when
        movieService.deleteMovie(movieId);

        // then
        assertEquals(0, movieRepository.count());
    }

    @Test
    void searchMovies_shouldReturnSearchedMovies() {
        // given
        String phrase = "Matrix";
        List<Movie> expectedMovies = Arrays.asList(
                new Movie(1L, "Matrix 1", 2020, "ACTION", "Description 1", 5),
                new Movie(2L, "Matrix 2", 2019, "COMEDY", "Description 2", 4)
        );
        Page<Movie> expectedPage = new PageImpl<>(expectedMovies);
        Pageable pageable = PageRequest.of(0, 10);
        when(movieRepository.findByTitleContainingIgnoreCase(phrase, pageable)).thenReturn(expectedPage);

        // when
        Page<Movie> actualPage = movieService.searchMovies(phrase, pageable);
        List<Movie> actualMovies = actualPage.getContent();

        // then
        assertEquals(expectedMovies, actualMovies);
    }

    @Test
    void rateMovie_shouldReturnRatedMovie() {
        // given
        long movieId = 1L;
        int rating = 4;
        Movie movieToRate = new Movie(movieId, "Matrix 1", 2020, "ACTION", "Description 1", 5);
        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movieToRate));
        when(movieRepository.save(movieToRate)).thenReturn(movieToRate);

        // when
        Movie actualMovie = movieService.rateMovie(movieId, rating);

        // then
        assertEquals(rating, actualMovie.getGrade());
    }

    @Test
    void filterMoviesByCategory_shouldReturnFilteredMovies() {
        // given
        String category = "ACTION";
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Movie> expectedMovies = new PageImpl<>(Arrays.asList(
                new Movie(1L, "Matrix 1", 2020, category, "Description 1", 5),
                new Movie(2L, "Matrix 2", 2019, category, "Description 2", 4)
        ));
        when(movieRepository.findByCategory(eq(category), any(PageRequest.class))).thenReturn(expectedMovies);

        // when
        Page<Movie> actualMovies = movieService.filterMoviesByCategory(category, pageRequest);

        // then
        assertEquals(expectedMovies, actualMovies);
    }
    @Test
    void rateMovie_shouldThrowIllegalArgumentExceptionForInvalidRating() {
        // given
        int invalidRating = -1;
        long movieId = 1L;

        // then
        assertThrows(IllegalArgumentException.class, () -> movieService.rateMovie(movieId, invalidRating));
    }
}
