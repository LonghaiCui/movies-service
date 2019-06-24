package com.longhai.moviesservice.api;

import com.longhai.moviesservice.event.EventPublisher;
import com.longhai.moviesservice.model.DataLoadEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;


@RestController
@RequestMapping("/csv")
public class FileController {
    private EventPublisher eventPublisher;

    @Autowired
    public FileController(
            EventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @PostMapping
    public ResponseEntity<String> loadData(@RequestParam(required = true) String fileName) {

        String id = UUID.randomUUID().toString();
        eventPublisher.publishEvent(DataLoadEvent.builder()
                .source(this)
                .id(id)
                .processName("DataLoadEvent")
                .fileName(fileName)
                .build());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Your data load request have been submitted successfully, your ticket number is " + id);
    }

}