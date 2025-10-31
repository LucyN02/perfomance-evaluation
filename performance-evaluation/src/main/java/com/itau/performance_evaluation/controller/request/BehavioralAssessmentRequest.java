package com.itau.performance_evaluation.controller.request;

import com.itau.performance_evaluation.model.enums.BehavioralEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;


import java.util.List;

@Data
public class BehavioralAssessmentRequest {
        @NotBlank
        private String employeeId;

        @Valid
        @Size(min = 4, max = 4, message = "A lista de avaliações deve conter exatamente 4 elementos.")
        private List<BehavioralData> behaviors;


        @Data
        public static class BehavioralData {
            private BehavioralEnum behavioral;
            @Min(1)
            @Max(5)
            private int grade;
       }
}


