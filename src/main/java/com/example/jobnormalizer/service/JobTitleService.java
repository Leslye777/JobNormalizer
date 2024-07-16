package com.example.jobnormalizer.service;

import com.example.jobnormalizer.utils.StringSimilarity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class JobTitleService {

    private static final Map<String, String> jobTitleMap = new HashMap<>();

    static {
        jobTitleMap.put("Software engineer", "Software engineer");
        jobTitleMap.put("Architect", "Architect");
        jobTitleMap.put("Quantity surveyor", "Quantity surveyor");
        jobTitleMap.put("Accountant", "Accountant");
    }

    public String normalizeTitle(String inputTitle) {
        String bestMatch = null;
        double highestScore = 0.0;

        for (Map.Entry<String, String> entry : jobTitleMap.entrySet()) {
            double score = StringSimilarity.calculateSimilarity(inputTitle, entry.getKey());
            if (score > highestScore) {
                highestScore = score;
                bestMatch = entry.getValue();
            }
        }

        return bestMatch;
    }
}