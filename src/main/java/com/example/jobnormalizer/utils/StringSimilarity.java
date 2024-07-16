package com.example.jobnormalizer.utils;

import java.util.HashSet;
import java.util.Set;

public class StringSimilarity {

    public static double calculateSimilarity(String s1, String s2) {
        Set<Character> set1 = stringToSet(s1);
        Set<Character> set2 = stringToSet(s2);

        Set<Character> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);

        Set<Character> union = new HashSet<>(set1);
        union.addAll(set2);

        return (double) intersection.size() / union.size();
    }

    private static Set<Character> stringToSet(String s) {
        Set<Character> set = new HashSet<>();
        for (char c : s.toCharArray()) {
            set.add(c);
        }
        return set;
    }
}