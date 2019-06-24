package com.longhai.moviesservice.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.longhai.moviesservice.model.DataLoadEvent;
import com.longhai.moviesservice.model.Movie;
import com.longhai.moviesservice.model.MoviesResponse;
import com.longhai.moviesservice.processor.DataLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static java.util.Arrays.asList;

@Component
public class EventListener<E extends DataLoadEvent> implements ApplicationListener<E> {

    private DataLoader dataLoader;
    private ObjectMapper objectMapper;

    @Autowired
    public EventListener(DataLoader dataLoader, ObjectMapper objectMapper) {
        this.dataLoader = dataLoader;
        this.objectMapper = objectMapper;
    }

    @Override
    public void onApplicationEvent(E event) {
        List<List<Movie>> movieList = new ArrayList<>();
        try {
            movieList = dataLoader.loadData(event.getFileName());

            MoviesResponse moviesResponse = MoviesResponse.builder()
                    .message(asList(movieList.get(0) == null ? "0" : movieList.get(0).size() + " movies inserted. Skip the following duplicates since they are already exists"))
                    .movies(movieList.get(1))
                    .build();

            String moviesResponseString = objectMapper.writeValueAsString(moviesResponse);
            System.out.println(format("event id: %s start ended ...", event.getId()) + moviesResponseString);

        } catch (InterruptedException ex) {
            System.out.println(format("event id: %s InterruptedException while handling event: \n", event.getId(), ex));
        } catch (URISyntaxException ex) {
            System.out.println(format("event id: %s URISyntaxException Exception happened while load the data:\n", event.getId()) + ex);
        } catch (IOException ex) {
            System.out.println(format("event id: %s IOException happened while load the data:\n", event.getId()) + ex);
        } catch (Exception ex) {
            System.out.println(format("event id: %s Unknown Exception happened while load the data:\n", event.getId()) + ex);
        }


    }
}
