package com.itau.performance_evaluation.controller.mapper;

import com.itau.performance_evaluation.controller.request.BehavioralAssessmentRequest;
import com.itau.performance_evaluation.controller.request.ChallengeAssessmentRequest;
import com.itau.performance_evaluation.controller.response.EvaluationsResponse;
import com.itau.performance_evaluation.model.BehavioralDetail;
import com.itau.performance_evaluation.model.ChallengeDetail;
import com.itau.performance_evaluation.model.PerformanceAssessment;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PerformanceAssessmentMapper {

    public static Set<ChallengeDetail> mapChallengeRequestToDetail(
            List<ChallengeAssessmentRequest.ChallengeData> requestData) {

        return requestData.stream()
                .map(request -> {
                    ChallengeDetail detail = new ChallengeDetail();
                    detail.setDescription(request.getChallenge());
                    detail.setScore(request.getGrade());

                    return detail;
                })
                .collect(Collectors.toSet());
    }


    public static Set<BehavioralDetail> mapBehavioralRequestToDetail(
            List<BehavioralAssessmentRequest.BehavioralData> requestData) {

        return requestData.stream()
                .map(request -> {
                    BehavioralDetail detail = new BehavioralDetail();
                    detail.setDescription(request.getBehavioral().getDescription());
                    detail.setScore(request.getGrade());
                    return detail;
                })
                .collect(Collectors.toSet());
    }

    public static EvaluationsResponse toEvaluationsResponse(PerformanceAssessment assessment) {
        Set<EvaluationsResponse.DetailResponse> challengeDetails = mapChallengeDetails(assessment.getChallenges());

        Set<EvaluationsResponse.DetailResponse> behaviorDetails = mapBehavioralDetails(assessment.getBehaviors());

        EvaluationsResponse.Evaluation evaluationData = EvaluationsResponse.Evaluation.builder()
                .employeeId(assessment.getEmployeeId())
                .challengeFinalAverage(assessment.getChallengeFinalAverage())
                .behaviorFinalAverage(assessment.getBehaviorFinalAverage())
                .challenges(challengeDetails)
                .behaviors(behaviorDetails)
                .build();

        return EvaluationsResponse.builder()
                .data(evaluationData)
                .build();
    }


    private static Set<EvaluationsResponse.DetailResponse> mapChallengeDetails(Set<ChallengeDetail> details) {
        if (details == null) return Set.of();

        return details.stream()
                .map(detail -> EvaluationsResponse.DetailResponse.builder()
                        .description(detail.getDescription())
                        .score(detail.getScore())
                        .build())
                .collect(Collectors.toSet());
    }

    private static Set<EvaluationsResponse.DetailResponse> mapBehavioralDetails(Set<BehavioralDetail> details) {
        if (details == null) return Set.of();

        return details.stream()
                .map(detail -> EvaluationsResponse.DetailResponse.builder()
                        .description(detail.getDescription())
                        .score(detail.getScore())
                        .build())
                .collect(Collectors.toSet());
    }
}
