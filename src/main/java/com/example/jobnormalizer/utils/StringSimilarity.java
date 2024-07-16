package com.example.jobnormalizer.utils;

import java.util.HashSet;
import java.util.Set;

public class StringSimilarity {

    public static double calculateSimilarity(String jobTitle1, String jobTitle2) {

        Set<Character> set1 = stringToSet(jobTitle1);
        Set<Character> set2 = stringToSet(jobTitle2);

        Set<Character> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);

        Set<Character> union = new HashSet<>(set1);
        union.addAll(set2);

        return (double) intersection.size() / union.size();
    }

    private static Set<Character> stringToSet(String jobTitle) {
        Set<Character> set = new HashSet<>();
        for (char c : jobTitle.toCharArray()) {
            set.add(c);
        }
        return set;
    }
}