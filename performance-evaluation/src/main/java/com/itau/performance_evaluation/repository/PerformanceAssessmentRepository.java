package com.itau.performance_evaluation.repository;

import com.itau.performance_evaluation.model.PerformanceAssessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PerformanceAssessmentRepository extends JpaRepository<PerformanceAssessment, Long> {

    @Query("SELECT performance FROM PerformanceAssessment performance " +
            "LEFT JOIN FETCH performance.challenges c " +
            "LEFT JOIN FETCH performance.behaviors b " +
            "WHERE performance.employeeId = :employeeId")
    Optional<PerformanceAssessment> findByEmployeeIdWithDetails(@Param("employeeId") String employeeId);

}
