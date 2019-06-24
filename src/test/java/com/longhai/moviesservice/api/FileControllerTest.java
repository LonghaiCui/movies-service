package com.longhai.moviesservice.api;

import com.longhai.moviesservice.event.EventPublisher;
import com.longhai.moviesservice.processor.DataLoader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class FileControllerTest {
    @Autowired
    private MockMvc mvc;

    @Mock
    private EventPublisher eventPublisher;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(new FileController(eventPublisher)).build();
    }

    @Test
    public void testLoadMovies() throws Exception {

        mvc.perform(post("/csv/")
                .param("fileName", "abd")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        verify(eventPublisher).publishEvent(any());
    }

    /*
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
    */

}