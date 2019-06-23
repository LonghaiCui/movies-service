package com.longhai.moviesservice;

import com.longhai.moviesservice.model.Customer;
import com.longhai.moviesservice.model.Movie;
import com.longhai.moviesservice.processor.DataLoader;
import com.longhai.moviesservice.repository.CustomerRepository;
import com.longhai.moviesservice.repository.MovieRepository;
import com.mongodb.MongoBulkWriteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.dao.DuplicateKeyException;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

@SpringBootApplication
public class MoviesServiceApplication implements CommandLineRunner {
	CustomerRepository repository;
	MovieRepository mrepository;
	@Autowired
	public MoviesServiceApplication(CustomerRepository repository, MovieRepository mrepository) {
		this.repository = repository;
		this.mrepository = mrepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(MoviesServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		DataLoader dl = new DataLoader();

		List<Movie> movies = dl.readAllExample();

		//db.movie.createIndex( { title: 1, director: 1 }, { unique: true } )
		movies.stream()
				.forEach(movie -> {
					try {
						mrepository.save(movie);
					} catch (DuplicateKeyException ex) {
						System.out.println("duplicate found -> " + movie);
					}
				});

		List<Movie> all = mrepository.findAll();

		for (Movie movie : all) {
			System.out.println(movie);
		}

		System.out.println("There are records -> " + all.size());
	}
}
