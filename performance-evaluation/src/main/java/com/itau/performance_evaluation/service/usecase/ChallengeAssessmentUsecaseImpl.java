package com.itau.performance_evaluation.service.usecase;

import com.itau.performance_evaluation.controller.Request.ChallengeAssessmentRequest;
import com.itau.performance_evaluation.model.PerformanceAssessment;
import com.itau.performance_evaluation.model.ChallengeDetail;
import com.itau.performance_evaluation.repository.PerformanceAssessmentRepository;
import com.itau.performance_evaluation.service.ChallengeAssessmentUsecase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ChallengeAssessmentUsecaseImpl implements ChallengeAssessmentUsecase {

    private final PerformanceAssessmentRepository repository;

    @Override
    @Transactional
    public void create(ChallengeAssessmentRequest request) {

        Optional<PerformanceAssessment> assessmentOpt = repository.findByEmployeeIdWithDetails(request.getEmployeeId());

        ChallengeAssessmentData newChallengeData = mapNewChallengeData(request);

        PerformanceAssessment assessment;

        if (assessmentOpt.isPresent()) {

            assessment = assessmentOpt.get();

            assessment.setChallengeFinalAverage(newChallengeData.finalAverage());

            Set<ChallengeDetail> challenges = assessment.getChallenges();

            challenges.clear();

            challenges.addAll(newChallengeData.details());

        } else {
            assessment = PerformanceAssessment.builder()
                    .employeeId(request.getEmployeeId())
                    .challenges(newChallengeData.details())
                    .behaviors(Set.of())
                    .challengeFinalAverage(newChallengeData.finalAverage())
                    .behaviorFinalAverage(null)
                    .build();
        }

        newChallengeData.details().forEach(detail -> detail.setAssessment(assessment));

        repository.save(assessment);
    }


    private record ChallengeAssessmentData(Set<ChallengeDetail> details, double finalAverage) {}

    private ChallengeAssessmentData mapNewChallengeData(ChallengeAssessmentRequest request) {

        List<ChallengeAssessmentRequest.ChallengeData> challengesRequest = request.getChallenges();

        double sumGrades = challengesRequest.stream()
                .mapToInt(ChallengeAssessmentRequest.ChallengeData::getGrade)
                .sum();

        double finalAverage = sumGrades / challengesRequest.size(); // Use size() do List, não 'count'

        Set<ChallengeDetail> details = challengesRequest.stream()
                .map(challenge -> ChallengeDetail.builder()
                        .description(challenge.getChallenge())
                        .score(challenge.getGrade())
                        .build())
                .collect(Collectors.toSet());

        return new ChallengeAssessmentData(details, finalAverage);
    }
}
