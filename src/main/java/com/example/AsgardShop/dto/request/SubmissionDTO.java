package com.example.AsgardShop.dto.request;

import lombok.Data;
import java.util.Map;

@Data
public class SubmissionDTO {
    private String studentName;
    // Map of question ID to selected answer
    private Map<Long, String> selectedAnswers;
} 