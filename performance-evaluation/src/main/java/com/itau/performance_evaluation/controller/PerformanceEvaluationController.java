package com.itau.performance_evaluation.controller;

import com.itau.performance_evaluation.controller.mapper.PerformanceAssessmentMapper;
import com.itau.performance_evaluation.controller.request.BehavioralAssessmentRequest;

import com.itau.performance_evaluation.controller.request.ChallengeAssessmentRequest;
import com.itau.performance_evaluation.controller.response.EvaluationsResponse;
import com.itau.performance_evaluation.model.BehavioralDetail;
import com.itau.performance_evaluation.model.ChallengeDetail;
import com.itau.performance_evaluation.model.PerformanceAssessment;
import com.itau.performance_evaluation.service.BehavioralAssessmentUsecase;
import com.itau.performance_evaluation.service.ChallengeAssessmentUsecase;
import com.itau.performance_evaluation.service.EvaluationsUsecase;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


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
        Set<BehavioralDetail> behavioralDetails =
                PerformanceAssessmentMapper.mapBehavioralRequestToDetail(request.getBehaviors());

        this.behavioralAssessmentUsecase.createOrUpdate(request.getEmployeeId(), behavioralDetails);
    }

    @PostMapping("/challenges")
    public void create(@Valid @RequestBody ChallengeAssessmentRequest request) {
        Set<ChallengeDetail> challengeDetails =
                PerformanceAssessmentMapper.mapChallengeRequestToDetail(request.getChallenges());

        this.challengeUsecase.createOrUpdate(request.getEmployeeId(),challengeDetails);
    }

    @GetMapping("/{employeeId}")
    public EvaluationsResponse findEvaluations(@PathVariable("employeeId") String employeeId) {
        PerformanceAssessment performanceAssessment = this.evaluationsUsecase.findByEmployeeId(employeeId);

        return PerformanceAssessmentMapper.toEvaluationsResponse(performanceAssessment);
    }
}
