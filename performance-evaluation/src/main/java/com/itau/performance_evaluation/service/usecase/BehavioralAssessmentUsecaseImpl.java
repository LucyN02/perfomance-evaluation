package com.itau.performance_evaluation.service.usecase;

import com.itau.performance_evaluation.controller.Request.BehavioralAssessmentRequest;
import com.itau.performance_evaluation.model.BehavioralDetail;
import com.itau.performance_evaluation.model.PerformanceAssessment;
import com.itau.performance_evaluation.repository.PerformanceAssessmentRepository;
import com.itau.performance_evaluation.service.BehavioralAssessmentUsecase;
import com.itau.performance_evaluation.controller.Request.BehavioralAssessmentRequest.BehavioralData;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BehavioralAssessmentUsecaseImpl implements BehavioralAssessmentUsecase {

    private final PerformanceAssessmentRepository repository;

    @Override
    @Transactional
    public void create(BehavioralAssessmentRequest request) {

        Optional<PerformanceAssessment> assessmentOpt = repository.findByEmployeeIdWithDetails(request.getEmployeeId());

        BehavioralAssessmentData newBehavioralData = mapNewBehavioralData(request);

        PerformanceAssessment assessment;

        if (assessmentOpt.isPresent()) {

            assessment = assessmentOpt.get();

            assessment.setBehaviorFinalAverage(newBehavioralData.finalAverage());

            Set<BehavioralDetail> behaviors = assessment.getBehaviors();

            behaviors.clear();

            behaviors.addAll(newBehavioralData.details());
        } else {
            assessment = PerformanceAssessment.builder()
                    .employeeId( request.getEmployeeId())
                    .challengeFinalAverage(null)
                    .behaviorFinalAverage(newBehavioralData.finalAverage())
                    .behaviors(newBehavioralData.details())
                    .challenges(Set.of())
                    .build();
        }

        newBehavioralData.details().forEach(detail -> detail.setAssessment(assessment));

        this.repository.save(assessment);
    }

    private record BehavioralAssessmentData(Set<BehavioralDetail> details, double finalAverage) {}

    private BehavioralAssessmentData mapNewBehavioralData(BehavioralAssessmentRequest request) {

        List<BehavioralData> behaviorsRequest = request.getBehaviors();

        double sumWeightedGrades = 0.0;
        int sumWeights = 0;

        for (BehavioralData data : behaviorsRequest) {

            int grade = data.getGrade();
            int weight = data.getBehavioral().getWeight();

            sumWeightedGrades += (double) grade * weight;
            sumWeights += weight;
        }


        double finalAverage = sumWeightedGrades / sumWeights;

        Set<BehavioralDetail> details = behaviorsRequest.stream()
                .map(data -> BehavioralDetail.builder()
                        .description(data.getBehavioral().getDescription())
                        .score(data.getGrade())
                        .build())
                .collect(Collectors.toSet());

        return new BehavioralAssessmentData(details, finalAverage);
    }

}
