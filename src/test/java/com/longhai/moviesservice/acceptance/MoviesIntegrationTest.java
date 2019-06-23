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

    @Before
    public void setUp() throws Exception {
        RestAssured.port = port;
        RestAssured.baseURI = "http://127.0.0.1";

        objectMapper = new ObjectMapper();
        jsonRequest = getJSON("/userRequest.json");
    }


    /*
        Given a GET request is received in /movies endpoint
        When the queryString is year and the value is valid (from 1900 to 2100)
        Then the service should return the movie list in that year
    */
    @Test
    public void testSearchMoviesByYear_whenYearIsValid_thenReturnMoviesInThatYear() {
        Response getResponse = given()
                .header("sessionId", "session123")
                .when()
                .contentType("application/json")
                .param("year", 2018)
                .get("/movies")
                .then()
                .statusCode(200)
                .extract()
                .response();

    }

    /*
        Given a GET request is received in /movies endpoint
        When the queryString is year and the value is not valid (before 1900 or after 2100)
        Then the service should return error response with 400 status code

    */
    @Test
    public void testSearchMoviesByYear_whenYearIsNotValid_thenReturn400() {
        Response getResponse = given()
                .header("sessionId", "session123")
                .when()
                .contentType("application/json")
                .param("year", 9999)
                .get("/movies")
                .then()
                .statusCode(400)
                .extract()
                .response();

    }

    /*
        Given a GET request is received in /movies endpoint
        When the queryStrings are "from" and "to"
        And the values are valid ("from" is bigger than 1900 and "to" is smaller than 2100)
        Then the service should return the movie list in that year
    */
    @Test
    public void testSearchMoviesByYear_whenQueryStringsAreValidFromAndTo_thenReturn400() {
        Response getResponse = given()
                .header("sessionId", "session123")
                .when()
                .contentType("application/json")
                .param("from", 2012)
                .param("to", 2013)
                .get("/movies")
                .then()
                .statusCode(400)
                .extract()
                .response();

    }

    /*
        Given a GET request is received in /movies endpoint
        When the queryStrings both "from" and "to" are present
        And the values are not valid ("from" is smaller than 1900 or "to" is bigger than 2100)
        Then the service should return error response with 400 status code
    */
    @Test
    public void testSearchMoviesByYear_whenQueryStringsFromAndToAreNotValid_thenReturn400() {
        Response getResponse = given()
                .header("sessionId", "session123")
                .when()
                .contentType("application/json")
                .param("from", 1000)
                .param("to", 3000)
                .get("/movies")
                .then()
                .statusCode(400)
                .extract()
                .response();
    }

    /*
        Given a GET request is received in /movies endpoint
        When the queryStrings "from" is bigger than "to"
        Then the service should return error response with 400 status code
    */
    @Test
    public void testSearchMoviesByYear_whenQueryStringsFromIsBiggerThanTo_thenReturn400() {
        Response getResponse = given()
                .header("sessionId", "session123")
                .when()
                .contentType("application/json")
                .param("from", 2020)
                .param("to", 2000)
                .get("/movies")
                .then()
                .statusCode(400)
                .extract()
                .response();
    }

    /*
        Given a GET request is received in /movies endpoint
        When only one of "from" and "to" is present
        Then the service should return error response with 400 status code
    */
    @Test
    public void testSearchMoviesByYear_whenOnlyFromIsPresent_thenReturn400() {
        Response getResponse = given()
                .header("sessionId", "session123")
                .when()
                .contentType("application/json")
                .param("from", 1000)
                .get("/movies")
                .then()
                .statusCode(400)
                .extract()
                .response();
    }

    /*
        Given a GET request is received in /movies endpoint
        When the none of the queryString "year", "from" or "to" is present
        Then the service should return the movie lists in default year 2016
    */
    @Test
    public void testSearchMoviesByYear_whenYearIsMissing_thenReturnDefault2016() {
        Response getResponse = given()
                .header("sessionId", "session123")
                .when()
                .contentType("application/json")
                .get("/movies")
                .then()
                .statusCode(200)
                .extract()
                .response();
    }

    private String getJSON(String path) throws Exception {
        URL url = this.getClass().getResource(path);
        return new String(Files.readAllBytes(Paths.get(url.getFile())));
    }
}