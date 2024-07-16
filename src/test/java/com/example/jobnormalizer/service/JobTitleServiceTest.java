package com.example.jobnormalizer.service;

import com.example.jobnormalizer.exception.InvalidJobTitleException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JobTitleServiceTest {

    private JobTitleService jobTitleService;

    @BeforeEach
    public void setUp() {
        jobTitleService = new JobTitleService();
    }

    @Test
    public void testNormalizeTitleValidTitle() {
        String result = jobTitleService.normalizeTitle("Java engineer");
        assertEquals("Software engineer", result);
    }

    @Test
    public void testNormalizeTitleEmptyTitle() {
        Exception exception = assertThrows(InvalidJobTitleException.class, () -> {
            jobTitleService.normalizeTitle("");
        });

        String expectedMessage = "The job title must not be null or empty";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testNormalizeTitleNullTitle() {
        Exception exception = assertThrows(InvalidJobTitleException.class, () -> {
            jobTitleService.normalizeTitle(null);
        });

        String expectedMessage = "The job title must not be null or empty";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testNormalizeTitleNoMatchFound() {
        Exception exception = assertThrows(InvalidJobTitleException.class, () -> {
            jobTitleService.normalizeTitle("*&%#");
        });

        String expectedMessage = "No suitable job title match found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
