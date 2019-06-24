package com.longhai.moviesservice.api;

import com.longhai.moviesservice.model.Movie;
import com.longhai.moviesservice.model.MoviesResponse;
import com.longhai.moviesservice.processor.DataLoader;
import com.longhai.moviesservice.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;


@RestController
@RequestMapping("/csv")
public class FileController {
    private DataLoader dataLoader;

    @Autowired
    public FileController(DataLoader dataLoader) {
        this.dataLoader = dataLoader;
    }

    @PostMapping
    public ResponseEntity<MoviesResponse> loadData(@RequestParam(required = true) String fileName) {
        List<List<Movie>> movieList = new ArrayList<>();
        List<String> errorMessages = new ArrayList<>();
        try {
            movieList = dataLoader.loadData(fileName);
        } catch (URISyntaxException ex) {
            errorMessages.add("URISyntaxException Exception happened while load the data:\n" + ex);
        } catch (IOException ex) {
            errorMessages.add("IOException happened while load the data:\n" + ex);
        } catch (Exception ex) {
            errorMessages.add("Unknown Exception happened while load the data:\n" + ex);
        }

        if(errorMessages.size() != 0) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MoviesResponse.builder()
                            .message(errorMessages)
                            .build());
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(MoviesResponse.builder()
                        .message(asList(movieList.get(0) == null ? "0" : movieList.get(0).size() + " movies inserted. Skip the following duplicates since they are already exists"))
                        .movies(movieList.get(1))
                        .build());
    }

}