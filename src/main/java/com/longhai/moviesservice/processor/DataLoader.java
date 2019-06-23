package com.longhai.moviesservice.processor;

import com.longhai.moviesservice.model.Movie;
import com.opencsv.CSVReader;

import java.io.Reader;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;


public class DataLoader {

    public DataLoader() {
    }

    public List<Movie> readAll(Reader reader) throws Exception {
        CSVReader csvReader = new CSVReader(reader);
        List<String[]> list = csvReader.readAll();

        List<Movie> movies = list.stream()
                .filter(record -> !"".equals(record[0]) && !"Rank".equals(record[0]))
                .map(record ->
                        Movie.builder()
                                .title(record[1])
                                .genre(asList(record[2].split(",")))
                                .description(record[3])
                                .director(record[4])
                                .actors(record[5])
                                .year(Integer.parseInt(record[6]))
                                .runtime(Integer.parseInt(record[7]))
                                .rating(Float.parseFloat(record[8]))
                                .votes(Integer.parseInt(record[9]))
                                .revenue("".equals(record[10]) ? null : Float.parseFloat(record[10]))
                                .metascore("".equals(record[11]) ? null : Integer.parseInt(record[11]))
                                .build())
                .collect(toList());


        reader.close();
        csvReader.close();
        return movies;
    }

    public List<Movie> readAllExample() throws Exception {
        URL systemResource = getClass().getClassLoader().getResource("csv/IMDB-Movie-Data_Assignment.csv");
        URI uri = systemResource.toURI();
        Path path = Paths.get(uri);

        Reader reader = Files.newBufferedReader(path);
        return readAll(reader);
    }

}
