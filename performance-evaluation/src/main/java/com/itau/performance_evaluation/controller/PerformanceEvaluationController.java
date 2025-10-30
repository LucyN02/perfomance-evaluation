package com.itau.performance_evaluation.controller;

import com.itau.performance_evaluation.controller.Request.BehavioralAssessmentRequest;

import com.itau.performance_evaluation.controller.Request.ChallengeAssessmentRequest;
import com.itau.performance_evaluation.controller.Response.EvaluationsResponse;
import com.itau.performance_evaluation.service.BehavioralAssessmentUsecase;
import com.itau.performance_evaluation.service.ChallengeAssessmentUsecase;
import com.itau.performance_evaluation.service.EvaluationsUsecase;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/performance-evaluations")
public class PerformanceEvaluationController {

    private final BehavioralAssessmentUsecase behavioralAssessmentUsecase;
    private final ChallengeAssessmentUsecase challengeUsecase;
    private final EvaluationsUsecase evaluationsUsecase;

    public PerformanceEvaluationController(BehavioralAssessmentUsecase behavioralAssessmentUsecase, ChallengeAssessmentUsecase challengeUsecase, EvaluationsUsecase evaluationsUsecase) {
        this.behavioralAssessmentUsecase = behavioralAssessmentUsecase;
        this.challengeUsecase = challengeUsecase;
        this.evaluationsUsecase = evaluationsUsecase;
    }

    @PostMapping("/behaviors")
    public void create(@Valid @RequestBody BehavioralAssessmentRequest request) {

        this.behavioralAssessmentUsecase.create(request);

    }

    @PostMapping("/challenges")
    public void create(@Valid @RequestBody ChallengeAssessmentRequest request) {

        this.challengeUsecase.create(request);

    }

    @GetMapping("/{employeeId}")
    public EvaluationsResponse findEvaluations(@PathVariable("employeeId") String employeeId) {
        return this.evaluationsUsecase.findByEmployeeId(employeeId);
    }

}
