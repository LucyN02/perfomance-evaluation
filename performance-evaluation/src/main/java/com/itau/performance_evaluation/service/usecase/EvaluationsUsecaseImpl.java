package com.itau.performance_evaluation.service.usecase;

import com.itau.performance_evaluation.model.PerformanceAssessment;
import com.itau.performance_evaluation.repository.PerformanceAssessmentRepository;
import com.itau.performance_evaluation.service.EvaluationsUsecase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class EvaluationsUsecaseImpl implements EvaluationsUsecase {

    private final PerformanceAssessmentRepository repository;

    @Override
    public PerformanceAssessment findByEmployeeId(String employeeId) {

        return repository.findByEmployeeIdWithDetails(employeeId)
                .orElseThrow(() -> new RuntimeException("Avaliação não encontrada para o ID: " + employeeId));
    }
}
