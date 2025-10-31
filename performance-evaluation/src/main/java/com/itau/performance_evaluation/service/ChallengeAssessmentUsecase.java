package com.itau.performance_evaluation.service;

import com.itau.performance_evaluation.model.ChallengeDetail;

import java.util.Set;

public interface ChallengeAssessmentUsecase {

    void createOrUpdate(String employeeId, Set<ChallengeDetail> challengeDetails);
}
