package com.itau.employee.controller.request;

import lombok.*;
import jakarta.validation.constraints.NotBlank;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRequest {
    @NotBlank(message = "O nome é obrigatório.")
    private String name;

    @NotBlank(message = "A data de admissão é obrigatória.")
    private String admissionDate;

    @NotBlank(message = "O cargo é obrigatório.")
    private String position;
}
