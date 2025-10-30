package com.itau.performance_evaluation.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@Builder
@Getter
@Setter
@Table(name = "tb_performance_assessment")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class PerformanceAssessment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_id")
    private String employeeId;

    @OneToMany(mappedBy = "assessment", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ChallengeDetail> challenges;

    @OneToMany(mappedBy = "assessment", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BehavioralDetail> behaviors;

    @Column(name = "challenge_final_average")
    private Double challengeFinalAverage;

    @Column(name = "behavior_final_average")
    private Double behaviorFinalAverage;
}
