package com.itau.performance_evaluation.repository;

import com.itau.performance_evaluation.model.BehavioralAssessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BehavioralRepository extends JpaRepository<BehavioralAssessment, Long> {
}
