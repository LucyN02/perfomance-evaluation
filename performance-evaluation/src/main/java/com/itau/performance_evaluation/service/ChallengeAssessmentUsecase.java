package com.itau.performance_evaluation.service;

import com.itau.performance_evaluation.controller.Request.ChallengeAssessmentRequest;

public interface ChallengeAssessmentUsecase {

    void create(ChallengeAssessmentRequest request);
}
