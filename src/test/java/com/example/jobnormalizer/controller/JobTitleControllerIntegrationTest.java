package com.example.jobnormalizer.controller;

import com.example.jobnormalizer.exception.GlobalExceptionHandler;
import com.example.jobnormalizer.service.JobTitleService;
import com.example.jobnormalizer.exception.InvalidJobTitleException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class JobTitleControllerIntegrationTest {

    private MockMvc mockMvc;

    @Mock
    private JobTitleService jobTitleService;

    @InjectMocks
    private JobTitleController jobTitleController;



    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(jobTitleController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @Test
    public void testNormalizeTitleValidTitle() throws Exception {
        when(jobTitleService.normalizeTitle(anyString())).thenReturn("Software engineer");

        mockMvc.perform(post("/api/normalize")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Java engineer\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Software engineer"));
    }

    @Test
    public void testNormalizeTitleEmptyTitle() throws Exception {
        when(jobTitleService.normalizeTitle(anyString())).thenThrow(new InvalidJobTitleException("The job title must not be null or empty"));

        mockMvc.perform(post("/api/normalize")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("The job title must not be null or empty"));
    }

    @Test
    public void testNormalizeTitleNullTitle() throws Exception {
        when(jobTitleService.normalizeTitle(anyString())).thenThrow(new InvalidJobTitleException("The job title must not be null or empty"));

        mockMvc.perform(post("/api/normalize")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \" \"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("The job title must not be null or empty"));
    }

    @Test
    public void testNormalizeTitleNoMatchFound() throws Exception {
        when(jobTitleService.normalizeTitle(anyString())).thenThrow(new InvalidJobTitleException("No suitable job title match found"));

        mockMvc.perform(post("/api/normalize")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"&¨*\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("No suitable job title match found"));
    }
}