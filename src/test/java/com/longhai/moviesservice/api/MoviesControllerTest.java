package com.longhai.moviesservice.api;

import com.longhai.moviesservice.processor.MoviesProcessor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class MoviesControllerTest {
    @Autowired
    private MockMvc mvc;

    @Mock
    private MoviesProcessor processor;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(new MoviesController(processor)).build();
    }

    @Test
    public void testGetMovies_whenYearIsValid_andGenreIsMissing_thenReturnsSuccess() throws Exception {

        mvc.perform(get("/movies/")
                .param("year", "2000")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        verify(processor).findMovies(2000, null);
    }

    @Test
    public void testGetMovies_whenYearIsInvalid_andGenreIsMissing_thenReturns400() throws Exception {

        mvc.perform(get("/movies/")
                .param("year", "4000")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());

        verifyZeroInteractions(processor);
    }

    @Test
    public void testGetMovies_whenFromAndToAreValid_andGenreIsMissing_thenReturnsSuccess() throws Exception {

        mvc.perform(get("/movies/")
                .param("from", "2015")
                .param("to", "2017")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        verify(processor).findMovies(2014, 2018, null);
    }

    @Test
    public void testGetMovies_whenFromAndToAreInvalid_andGenreIsMissing_thenReturns400() throws Exception {

        mvc.perform(get("/movies/")
                .param("from", "-2000")
                .param("to", "3000")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());

        verifyZeroInteractions(processor);
    }

    @Test
    public void testGetMovies_whenOnlyFromIsPresent_andGenreIsMissing_thenReturns400() throws Exception {

        mvc.perform(get("/movies/")
                .param("from", "2019")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());

        verifyZeroInteractions(processor);
    }

    @Test
    public void testGetMovies_whenFromIsBiggerThanTo_andGenreIsMissing_thenReturns400() throws Exception {

        mvc.perform(get("/movies/")
                .param("from", "2019")
                .param("to", "2000")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());

        verifyZeroInteractions(processor);
    }

    @Test
    public void testGetMovies_whenYearIsMissing_andGenreIsMissing_thenReturnsMoviesIn2016() throws Exception {

        mvc.perform(get("/movies/")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        verify(processor).findMovies(2016, null);
    }

    @Test
    public void testGetMovies_whenYearIsValid_andGenreIsNotMissing_thenReturnsSuccess() throws Exception {

        mvc.perform(get("/movies/")
                .param("year", "2000")
                .param("genre", "Action")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        verify(processor).findMovies(2000, "Action");
    }

    @Test
    public void testGetMovies_whenFromAndToAreValid_andGenreIsNotMissing_thenReturnsSuccess() throws Exception {

        mvc.perform(get("/movies/")
                .param("from", "2015")
                .param("to", "2017")
                .param("genre", "Action")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        verify(processor).findMovies(2014, 2018, "Action");
    }

}