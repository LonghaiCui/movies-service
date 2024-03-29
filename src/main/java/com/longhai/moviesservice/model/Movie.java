package com.longhai.moviesservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(value = {
        "id",
        "director",
        "actors",
        "votes",
        "revenue",
        "metascore"
})

public class Movie {
    @Id
    private String id;

    private String title;
    private List<String> genre;
    private String description;
    private String director;
    private String actors;
    private Integer year;
    private Integer runtime;
    private Float rating;
    private Integer votes;
    private Float revenue;
    private Integer metascore;

}
