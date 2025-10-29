package com.itau.performance_evaluation.controller;

import com.itau.performance_evaluation.controller.Request.BehavioralAssessmentRequest;

import com.itau.performance_evaluation.service.BehavioralAssessmentUsecase;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/behavioral-evaluations")
public class PerformanceEvaluationController {

    private final BehavioralAssessmentUsecase behavioralAssessmentUsecase;

    public PerformanceEvaluationController(BehavioralAssessmentUsecase behavioralAssessmentUsecase) {
        this.behavioralAssessmentUsecase = behavioralAssessmentUsecase;
    }

    @PostMapping
    public void create(@Valid @RequestBody BehavioralAssessmentRequest request) {

        this.behavioralAssessmentUsecase.create(request);

    }

}
