package com.itau.performance_evaluation.service;

import com.itau.performance_evaluation.model.PerformanceAssessment;

public interface EvaluationsUsecase {

    PerformanceAssessment findByEmployeeId(String employeeId);
}
