package com.itau.performance_evaluation.service;

import com.itau.performance_evaluation.model.BehavioralDetail;

import java.util.Set;

public interface BehavioralAssessmentUsecase {

    void createOrUpdate(String employeeId, Set<BehavioralDetail> behaviorsDetails);
}
