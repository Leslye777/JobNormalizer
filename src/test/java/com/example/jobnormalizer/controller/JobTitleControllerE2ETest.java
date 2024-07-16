package com.example.jobnormalizer.controller;

import com.example.jobnormalizer.model.JobTitle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JobTitleControllerE2ETest {

    @LocalServerPort
    private int port;

    private String baseUrl;

    private RestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
        baseUrl = "http://localhost:" + port + "/api/normalize";
        restTemplate = new RestTemplate();
    }

    @Test
    public void testNormalizeTitleValidTitle() {
        JobTitle jobTitle = new JobTitle("Java engineer");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<JobTitle> request = new HttpEntity<>(jobTitle, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(baseUrl, request, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Software engineer", response.getBody());
    }

    @Test
    public void testNormalizeTitleEmptyTitle() {
        JobTitle jobTitle = new JobTitle("");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<JobTitle> request = new HttpEntity<>(jobTitle, headers);

        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () -> {
            restTemplate.postForEntity(baseUrl, request, String.class);
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("The job title must not be null or empty", exception.getResponseBodyAsString());
    }

    @Test
    public void testNormalizeTitleNullTitle() {
        JobTitle jobTitle = new JobTitle((String) null);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<JobTitle> request = new HttpEntity<>(jobTitle, headers);

        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () -> {
            restTemplate.postForEntity(baseUrl, request, String.class);
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("The job title must not be null or empty", exception.getResponseBodyAsString());
    }

    @Test
    public void testNormalizeTitleNoMatchFound() {
        JobTitle jobTitle = new JobTitle("@#$%");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<JobTitle> request = new HttpEntity<>(jobTitle, headers);

        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () -> {
            restTemplate.postForEntity(baseUrl, request, String.class);
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("No suitable job title match found", exception.getResponseBodyAsString());
    }
}
