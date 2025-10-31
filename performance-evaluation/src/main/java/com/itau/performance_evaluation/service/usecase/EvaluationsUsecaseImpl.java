package com.itau.performance_evaluation.service.usecase;

import com.itau.performance_evaluation.model.PerformanceAssessment;
import com.itau.performance_evaluation.repository.PerformanceAssessmentRepository;
import com.itau.performance_evaluation.service.EvaluationsUsecase;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@AllArgsConstructor
public class EvaluationsUsecaseImpl implements EvaluationsUsecase {

    private final PerformanceAssessmentRepository repository;

    @Override
    public PerformanceAssessment findByEmployeeId(String employeeId) {

        log.info("Iniciando a criação/atualização de desafios para employeeId: {}", employeeId);

        return repository.findByEmployeeIdWithDetails(employeeId)
                .orElseThrow(() -> {
                    log.error("Erro ao processar a avaliação para employeeId: {}", employeeId);
                    return new RuntimeException("Falha no processo de avaliação.");
                });
    }
}
