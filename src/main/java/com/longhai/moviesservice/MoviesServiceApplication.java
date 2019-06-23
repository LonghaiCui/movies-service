package com.longhai.moviesservice;

import com.longhai.moviesservice.model.Customer;
import com.longhai.moviesservice.model.Movie;
import com.longhai.moviesservice.processor.DataLoader;
import com.longhai.moviesservice.repository.CustomerRepository;
import com.longhai.moviesservice.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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

		mrepository.saveAll(movies);

		for (Movie movie : mrepository.findAll()) {
			System.out.println(movie);
		}

//		repository.deleteAll();
//
//		// save a couple of customers
//		repository.save(new Customer("Alice", "Smith"));
//		repository.save(new Customer("Bob", "Smith"));
//
//		// fetch all customers
//		System.out.println("Customers found with findAll():");
//		System.out.println("-------------------------------");
//		for (Customer customer : repository.findAll()) {
//			System.out.println(customer);
//		}
//		System.out.println();
//
//		// fetch an individual customer
//		System.out.println("Customer found with findByFirstName('Alice'):");
//		System.out.println("--------------------------------");
//		System.out.println(repository.findByFirstName("Alice"));
//
//		System.out.println("Customers found with findByLastName('Smith'):");
//		System.out.println("--------------------------------");
//		for (Customer customer : repository.findByLastName("Smith")) {
//			System.out.println(customer);
//		}

	}
}
