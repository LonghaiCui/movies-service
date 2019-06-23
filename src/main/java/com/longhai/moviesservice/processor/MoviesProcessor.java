package com.longhai.moviesservice.processor;

import com.longhai.moviesservice.model.Movie;
import com.longhai.moviesservice.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class MoviesProcessor {
    private MovieRepository repository;

    @Autowired
    public MoviesProcessor(MovieRepository repository) {
        this.repository = repository;
    }

    public List<Movie> findMovies(Integer year, String genre) {
        List<Movie> movies = StringUtils.isEmpty(genre) ?
                repository.findByYearOrderByRating(year) :
                repository.findByYearAndGenreContainingOrderByRating(year, genre);
        return movies;
    }

    public List<Movie> findMovies(Integer from, int to, String genre) {
        List<Movie> movies = StringUtils.isEmpty(genre) ?
                repository.findByYearBetweenOrderByRating(from, to) :
                repository.findByYearBetweenAndGenreContainingOrderByRating(from, to, genre);
        return movies;
    }
}
