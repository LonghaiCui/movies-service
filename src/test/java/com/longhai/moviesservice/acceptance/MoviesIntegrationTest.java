package com.longhai.moviesservice.acceptance;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.longhai.moviesservice.MoviesServiceApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = MoviesServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class MoviesIntegrationTest {
    @Value("${local.server.port}")
    private int port;

    private static ObjectMapper objectMapper;

    private String jsonRequest;
//    private User user;

    @Before
    public void setUp() throws Exception {
        RestAssured.port = port;
        RestAssured.baseURI = "http://127.0.0.1";

        objectMapper = new ObjectMapper();
        jsonRequest = getJSON("/userRequest.json");
//        user = objectMapper.readValue(jsonRequest, User.class);
    }


    @Test
    public void test() {
        Response response =
                given()
                        .header("sessionId", "session123")
                        .when()
                        .get("/movies")
                        .then()
                        .statusCode(201)
                        .extract()
                        .response();

        UserResponse userResponse = response.getBody().as(UserResponse.class);

        assertEquals(UserResponse.builder()
                        .status("201")
                        .message("User is created successfully with email longhaicui.work@gmail.com")
                        .build(),
                userResponse);
    }

    private String getJSON(String path) throws Exception {
        URL url = this.getClass().getResource(path);
        return new String(Files.readAllBytes(Paths.get(url.getFile())));
    }
}