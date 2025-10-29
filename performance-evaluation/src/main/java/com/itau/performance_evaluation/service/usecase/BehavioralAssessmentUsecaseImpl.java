package com.itau.performance_evaluation.service.usecase;

import com.itau.performance_evaluation.controller.Request.BehavioralAssessmentRequest;
import com.itau.performance_evaluation.model.BehavioralAssessment;
import com.itau.performance_evaluation.model.BehavioralEnum;
import com.itau.performance_evaluation.repository.BehavioralRepository;
import com.itau.performance_evaluation.service.BehavioralAssessmentUsecase;
import com.itau.performance_evaluation.controller.Request.BehavioralAssessmentRequest.BehavioralData;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BehavioralAssessmentUsecaseImpl implements BehavioralAssessmentUsecase {

    private final BehavioralRepository behavioralRepository;

    @Override
    public void create(BehavioralAssessmentRequest input) {

        BehavioralAssessment model = mapAndCalculateAssessment(input);

        this.behavioralRepository.save(model);
    }

    private BehavioralAssessment mapAndCalculateAssessment(BehavioralAssessmentRequest request) {

        Map<BehavioralEnum, Integer> gradesMap = request.getBehavioralAssessment().stream()
                .collect(Collectors.toMap(
                        BehavioralData::getBehavioral,
                        BehavioralData::getGrade
                ));

        double sumWeightedGrades = 0.0;
        int sumWeights = 0;


        for (Map.Entry<BehavioralEnum, Integer> entry : gradesMap.entrySet()) {
            BehavioralEnum behavioral = entry.getKey();
            int grade = entry.getValue();
            int weight = behavioral.getWeight();

            sumWeightedGrades += (double) grade * weight;
            sumWeights += weight;
        }

        double finalAverage = sumWeightedGrades / sumWeights;


        return BehavioralAssessment.builder()
                .employeeId(request.getEmployeeId())
                .behavioral_1(gradesMap.getOrDefault(BehavioralEnum.BEHAVIORAL_1, 0))
                .behavioral_2(gradesMap.getOrDefault(BehavioralEnum.BEHAVIORAL_2, 0))
                .behavioral_3(gradesMap.getOrDefault(BehavioralEnum.BEHAVIORAL_3, 0))
                .behavioral_4(gradesMap.getOrDefault(BehavioralEnum.BEHAVIORAL_4, 0))
                .final_average(finalAverage)
                .build();
    }
}
