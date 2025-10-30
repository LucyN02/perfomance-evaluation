package com.itau.performance_evaluation.service.usecase;

import com.itau.performance_evaluation.controller.Response.EvaluationsResponse;
import com.itau.performance_evaluation.model.PerformanceAssessment;
import com.itau.performance_evaluation.repository.PerformanceAssessmentRepository;
import com.itau.performance_evaluation.service.EvaluationsUsecase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EvaluationsUsecaseImpl implements EvaluationsUsecase {

    private final PerformanceAssessmentRepository repository;

    @Override
    public EvaluationsResponse findByEmployeeId(String employeeId) {

        PerformanceAssessment assessment = repository.findByEmployeeIdWithDetails(employeeId)
                .orElseThrow(() -> new RuntimeException("Avaliação não encontrada para o ID: " + employeeId));

        return mapToResponse(assessment);
    }

    private EvaluationsResponse mapToResponse(PerformanceAssessment assessment) {

        Set<EvaluationsResponse.DetailResponse> challengeResponses = assessment.getChallenges().stream()
                .map(detail -> EvaluationsResponse.DetailResponse.builder()
                        .description(detail.getDescription())
                        .score(detail.getScore())
                        .build())
                .collect(Collectors.toSet());


        Set<EvaluationsResponse.DetailResponse> behaviorResponses = assessment.getBehaviors().stream()
                .map(detail -> EvaluationsResponse.DetailResponse.builder()
                        .description(detail.getDescription())
                        .score(detail.getScore())
                        .build())
                .collect(Collectors.toSet());


        return EvaluationsResponse.builder()
                .data(
                        EvaluationsResponse.Evaluation.builder()
                                .employeeId(assessment.getEmployeeId())
                                .challengeFinalAverage(assessment.getChallengeFinalAverage())
                                .behaviorFinalAverage(assessment.getBehaviorFinalAverage())
                                .challenges(challengeResponses)
                                .behaviors(behaviorResponses)
                                .build()
                ).build();
    }

}
