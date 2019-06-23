package com.longhai.moviesservice.repository;

import com.longhai.moviesservice.model.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends MongoRepository<Movie, String> {
    List<Movie> findByYearBetweenAndGenreContainingOrderByRating(int from, int to, String genre);
    List<Movie> findByYearBetweenOrderByRating(int from, int to);
    List<Movie> findByYearAndGenreContainingOrderByRating(Integer  year, String genre);
    List<Movie> findByYearOrderByRating(Integer year);
}
