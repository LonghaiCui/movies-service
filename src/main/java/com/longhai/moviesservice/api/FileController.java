package com.longhai.moviesservice.api;

import com.longhai.moviesservice.model.MoviesResponse;
import com.longhai.moviesservice.processor.DataLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/csv")
public class FileController {
    private DataLoader dataLoader;

    @Autowired
    public FileController(DataLoader dataLoader) {
        this.dataLoader = dataLoader;
    }

    @PostMapping
    public ResponseEntity<String> loadData(
            @RequestParam(required = true) String fileName) {
        try {
            dataLoader.loadData(fileName);
        } catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Exception happened while load the data");
        }
        ResponseEntity<String> responseEntity = ResponseEntity
                .status(HttpStatus.OK)
                .body("Successfully load the data");
        return responseEntity;
    }

}