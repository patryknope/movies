package com.paszkowski.movies.repository;

import com.paszkowski.movies.model.Category;
import com.paszkowski.movies.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    List<Movie> findByTitleContainingIgnoreCase(String phrase);
    List<Movie> findByCategory(Category category);
    boolean existsByTitle(String title);


}
