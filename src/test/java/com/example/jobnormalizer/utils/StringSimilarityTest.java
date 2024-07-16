package com.example.jobnormalizer.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StringSimilarityTest {

    @Test
    public void testCalculateSimilaritySameTitles() {
        double similarity = StringSimilarity.calculateSimilarity("Software engineer", "Software engineer");
        assertEquals(1.0, similarity, 0.001);
    }

    @Test
    public void testCalculateSimilarityDifferentTitles() {
        double similarity = StringSimilarity.calculateSimilarity("Software engineer", "Accountant");
        assertEquals(0.26666666666666666, similarity, 0.001);
    }

    @Test
    public void testCalculateSimilaritySimilarTitles() {
        double similarity = StringSimilarity.calculateSimilarity("Java engineer", "Software engineer");
        assertTrue(similarity > 0.0 && similarity < 1.0);
    }

}
