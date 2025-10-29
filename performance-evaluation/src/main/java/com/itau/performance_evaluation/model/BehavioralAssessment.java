package com.itau.performance_evaluation.model;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@Table(name = "tb_behavioral")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class BehavioralAssessment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String employeeId;

    private int behavioral_1;

    private int behavioral_2;

    private int behavioral_3;

    private int behavioral_4;

    private double final_average;

}
