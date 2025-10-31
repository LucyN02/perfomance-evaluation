package com.itau.performance_evaluation.controller.response;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class EvaluationsResponse {

    public Evaluation data;

    @Data
    @Builder
    public static class Evaluation {
        private String employeeId;

        private Double challengeFinalAverage;

        private Double behaviorFinalAverage;

        private Set<DetailResponse> challenges;

        private Set<DetailResponse> behaviors;
    }

    @Data
    @Builder
    public static class DetailResponse {
        private String description;
        private int score;
    }
}

