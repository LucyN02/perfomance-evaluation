package com.itau.performance_evaluation.service.usecase;

import com.itau.performance_evaluation.model.PerformanceAssessment;
import com.itau.performance_evaluation.model.ChallengeDetail;
import com.itau.performance_evaluation.repository.PerformanceAssessmentRepository;
import com.itau.performance_evaluation.service.ChallengeAssessmentUsecase;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@AllArgsConstructor
public class ChallengeAssessmentUsecaseImpl implements ChallengeAssessmentUsecase {

    private final PerformanceAssessmentRepository repository;

    @Override
    @Transactional
    public void createOrUpdate(String employeeId, Set<ChallengeDetail> challengeDetails) {

        log.info("Iniciando a criação/atualização de desafios para employeeId: {}", employeeId);

        Optional<PerformanceAssessment> assessmentOpt = repository.findByEmployeeIdWithDetails(employeeId);

        PerformanceAssessment assessment;

        double sumGrades = challengeDetails.stream()
                .mapToInt(ChallengeDetail::getScore)
                .sum();

        double finalAverage = sumGrades / challengeDetails.size();

        if (assessmentOpt.isPresent()) {

            assessment = assessmentOpt.get();

            assessment.setChallengeFinalAverage(finalAverage);

            Set<ChallengeDetail> challenges = assessment.getChallenges();

            challenges.clear();

            challenges.addAll(challengeDetails);

        } else {
            assessment = PerformanceAssessment.builder()
                    .employeeId(employeeId)
                    .challenges(challengeDetails)
                    .behaviors(Set.of())
                    .challengeFinalAverage(finalAverage)
                    .behaviorFinalAverage(null)
                    .build();
        }

        challengeDetails.forEach(detail -> detail.setAssessment(assessment));

        repository.save(assessment);

        log.info("Avaliação salva com sucesso.");
    }
}
