package com.itau.performance_evaluation.service;

import com.itau.performance_evaluation.controller.Request.BehavioralAssessmentRequest;

public interface BehavioralAssessmentUsecase {
    void create(BehavioralAssessmentRequest behavioralAssessment);
}
