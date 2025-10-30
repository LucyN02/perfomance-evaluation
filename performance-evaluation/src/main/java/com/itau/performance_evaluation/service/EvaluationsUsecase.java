package com.itau.performance_evaluation.service;

import com.itau.performance_evaluation.controller.Response.EvaluationsResponse;

public interface EvaluationsUsecase {

    EvaluationsResponse findByEmployeeId(String employeeId);
}
