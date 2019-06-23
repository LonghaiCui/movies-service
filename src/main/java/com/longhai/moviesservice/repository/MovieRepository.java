package com.longhai.moviesservice.repository;

import com.longhai.moviesservice.model.Customer;
import com.longhai.moviesservice.model.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MovieRepository extends MongoRepository<Movie, String> {
}
