package com.paszkowski.movies.repository;

import com.paszkowski.movies.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MovieRepository extends PagingAndSortingRepository<Movie, Long> {

    Page<Movie> findAll(Pageable pageable);

    List<Movie> findByTitleContainingIgnoreCase(String phrase);
    Page<Movie> findByTitleContainingIgnoreCase(String phrase, Pageable pageable);

    List<Movie> findByCategory(String category);
    Page<Movie> findByCategory(String category, Pageable pageable);
    boolean existsByTitle(String title);
}
