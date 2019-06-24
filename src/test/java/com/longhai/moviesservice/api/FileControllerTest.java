package com.longhai.moviesservice.api;

import com.longhai.moviesservice.processor.DataLoader;
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

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class FileControllerTest {
    @Autowired
    private MockMvc mvc;

    @Mock
    private DataLoader dataLoader;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(new FileController(dataLoader)).build();
        when(dataLoader.loadData("abd"))
                .thenReturn(Collections.emptyList());
    }

    @Test
    public void testLoadMovies() throws Exception {

        mvc.perform(post("/csv/")
                .param("fileName", "abd")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        verify(dataLoader).loadData("abc");
    }

    @Test
    public void testLoadMovies_whenFileNameIsMissing_return400() throws Exception {

        mvc.perform(post("/csv/")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());

        verifyZeroInteractions(dataLoader);
    }

    @Test(expected = Exception.class)
    public void testLoadMovies_ExceptionHappensWhileWritingToDatabase_return500() throws Exception {

        mvc.perform(post("/csv/")
                .param("fileName", "abd")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isInternalServerError());

        verifyZeroInteractions(dataLoader);
    }

}