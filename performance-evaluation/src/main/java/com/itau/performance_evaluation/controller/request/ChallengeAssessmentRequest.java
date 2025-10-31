package com.itau.performance_evaluation.controller.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class ChallengeAssessmentRequest {
    @NotBlank
    private String employeeId;

    @Valid
    @Size(min = 2, max = 4, message = "A lista de desafios deve conter no min 2 e no max 4 elementos.")
    private List<ChallengeData> challenges;


    @Data
    public static class ChallengeData {
        private String challenge;
        @Min(1)
        @Max(5)
        private int grade;
    }
}