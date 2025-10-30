package com.itau.performance_evaluation.model;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@Table(name = "tb_behavioral_detail")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class BehavioralDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private int score;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assessment_id", nullable = false)
    private PerformanceAssessment assessment;

}
