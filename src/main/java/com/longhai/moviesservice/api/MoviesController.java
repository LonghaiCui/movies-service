package com.longhai.moviesservice.api;

import com.longhai.moviesservice.model.Movie;
import com.longhai.moviesservice.model.MoviesResponse;
import com.longhai.moviesservice.processor.MoviesProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;


@RestController
@RequestMapping("/movies")
public class MoviesController {
    private MoviesProcessor processor;

    @Autowired
    public MoviesController(MoviesProcessor processor) {
        this.processor = processor;
    }

    @GetMapping
    public ResponseEntity<MoviesResponse> getMovies(
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer from,
            @RequestParam(required = false) Integer to,
            @RequestParam(required = false) String genre) {

        List<String> errorMessages = validateRequest(year, from, to);

        if (errorMessages.size() != 0) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(MoviesResponse.builder()
                            .message(errorMessages)
                            .build());
        }

        List<Movie> movies = findMovies(year, from, to, genre);

        ResponseEntity<MoviesResponse> responseEntity = ResponseEntity
                .status(HttpStatus.OK)
                .body(MoviesResponse.builder()
                        .message(asList(movies.size() + " movies found."))
                        .movies(movies)
                        .build());

        return responseEntity;
    }

    private List<String> validateRequest(Integer year, Integer from, Integer to) {
        List<String> errorMessages = new ArrayList<>();
        if (year != null && (year < 1900 || year > 2100)) {
            errorMessages.add("Year must be between 1900 and 2100");
        }

        if ((from != null && to == null) || (to != null && from == null)) {
            errorMessages.add("From and To must be present together");
        } else if (from != null && to != null) {
            if (from < 1900 || from > 2100) {
                errorMessages.add("Year from must be between 1900 and 2100");
            }
            if (to < 1900 || to > 2100) {
                errorMessages.add("Year to must be between 1900 and 2100");
            }
            if (from > to) {
                errorMessages.add("From year must be smaller than to year");
            }
        }

        return errorMessages;
    }

    private List<Movie> findMovies(Integer year, Integer from, Integer to, String genre) {
        List<Movie> movies = new ArrayList<>();
        if(from != null && to != null) {
            movies = processor.findMovies(from-1, to+1, genre);
        } else {
            year = (year == null) ? 2016 : year;

            movies = processor.findMovies(year, genre);
        }

        return movies;
    }
}
