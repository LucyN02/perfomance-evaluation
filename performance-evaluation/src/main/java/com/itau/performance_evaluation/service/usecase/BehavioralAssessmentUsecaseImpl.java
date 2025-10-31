package com.itau.performance_evaluation.service.usecase;

import com.itau.performance_evaluation.model.BehavioralDetail;
import com.itau.performance_evaluation.model.PerformanceAssessment;
import com.itau.performance_evaluation.model.enums.BehavioralEnum;
import com.itau.performance_evaluation.repository.PerformanceAssessmentRepository;
import com.itau.performance_evaluation.service.BehavioralAssessmentUsecase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.Set;


@Service
@AllArgsConstructor
public class BehavioralAssessmentUsecaseImpl implements BehavioralAssessmentUsecase {

    private final PerformanceAssessmentRepository repository;

    @Override
    @Transactional
    public void createOrUpdate(String employeeId, Set<BehavioralDetail> behaviorsDetails) {

        Optional<PerformanceAssessment> assessmentOpt = repository.findByEmployeeIdWithDetails(employeeId);

        double sumWeightedGrades = 0.0;

        int sumWeights = 0;

        for (BehavioralDetail behavioral : behaviorsDetails) {

            int grade = behavioral.getScore();
            int weight = BehavioralEnum.getByDescription(behavioral.getDescription()).getWeight();

            sumWeightedGrades += (double) grade * weight;
            sumWeights += weight;
        }

        double finalAverage = sumWeightedGrades / sumWeights;

        PerformanceAssessment assessment;

        if (assessmentOpt.isPresent()) {

            assessment = assessmentOpt.get();

            assessment.setBehaviorFinalAverage(finalAverage);

            Set<BehavioralDetail> behaviors = assessment.getBehaviors();

            behaviors.clear();

            behaviors.addAll(behaviorsDetails);
        } else {
            assessment = PerformanceAssessment.builder()
                    .employeeId(employeeId)
                    .challengeFinalAverage(null)
                    .behaviorFinalAverage(finalAverage)
                    .behaviors(behaviorsDetails)
                    .challenges(Set.of())
                    .build();
        }

        behaviorsDetails.forEach(detail -> detail.setAssessment(assessment));

        this.repository.save(assessment);
    }
}
