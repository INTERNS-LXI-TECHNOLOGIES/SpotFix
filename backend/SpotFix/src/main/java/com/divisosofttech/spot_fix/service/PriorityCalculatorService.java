package com.divisosofttech.spot_fix.service;

import com.divisosofttech.spot_fix.domain.enumeration.Priority;
import org.springframework.stereotype.Service;

@Service
public class PriorityCalculatorService {

    public Priority calculatePriority(String title, String description) {

        String text = ((title == null ? "" : title) + " "
                + (description == null ? "" : description)).toLowerCase();

        int score = 0;

        // Emergency keywords
        if (containsAny(text,
                "accident",
                "fire",
                "danger",
                "life threatening",
                "emergency")) {
            score += 5;
        }

        // High priority keywords
        if (containsAny(text,
                "hospital",
                "school",
                "flood",
                "electric shock",
                "broken electric pole",
                "major road")) {
            score += 4;
        }

        // Medium priority keywords
        if (containsAny(text,
                "pothole",
                "street light",
                "garbage",
                "drainage",
                "water leakage")) {
            score += 2;
        }

        // General issues
        if (containsAny(text,
                "road",
                "waste",
                "traffic",
                "parking")) {
            score += 1;
        }

        if (score >= 5) {
            return Priority.URGENT;
        }

        if (score >= 4) {
            return Priority.HIGH;
        }

        if (score >= 2) {
            return Priority.MEDIUM;
        }

        return Priority.LOW;
    }

    private boolean containsAny(String text, String... keywords) {
        for (String keyword : keywords) {
            if (text.contains(keyword)) {
                return true;
            }
        }

        return false;
    }
}
