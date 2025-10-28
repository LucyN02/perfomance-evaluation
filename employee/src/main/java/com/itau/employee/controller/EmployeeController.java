package com.itau.employee.controller;

import com.itau.employee.controller.request.EmployeeRequest;
import com.itau.employee.model.Employee;
import com.itau.employee.service.EmployeeUsecase;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    private final EmployeeUsecase employeeUsecase;

    public EmployeeController(EmployeeUsecase employeeUsecase) {
        this.employeeUsecase = employeeUsecase;
    }

    @PostMapping
    public void create (@Valid @RequestBody EmployeeRequest employeeRequest) {

        this.employeeUsecase.createEmployee(createEmployee(employeeRequest));
    }

    private Employee createEmployee(EmployeeRequest employeeRequest) {
        return (Employee.builder()
                .name(employeeRequest.getName())
                .admission_date(employeeRequest.getAdmissionDate())
                .employee_position(employeeRequest.getPosition())
                .build());
    }
}
