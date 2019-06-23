package com.longhai.moviesservice.processor;

import com.longhai.moviesservice.repository.MovieRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

public class MoviesProcessorTest {
    @InjectMocks
    private MoviesProcessor moviesProcessor;

    private MovieRepository repository;

    @Before
    public void setUp() throws Exception {
        repository = Mockito.mock(MovieRepository.class);
        moviesProcessor = new MoviesProcessor(repository);
        when(repository.findByYearOrderByRating(anyInt()))
                .thenReturn(Collections.emptyList());
    }

    @Test
    public void testFindMovies_whenYearIsNull() {

        moviesProcessor.findMovies(null, null);
    }
}