package com.itau.employee.service.usecase;

import com.itau.employee.model.Employee;
import com.itau.employee.repository.EmployeeRepository;
import com.itau.employee.service.EmployeeUsecase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmployeeUsecaseImpl implements EmployeeUsecase {

    private final EmployeeRepository employeeRepository;

    @Override
    public void createEmployee(Employee employee){
        this.employeeRepository.save(employee);
    }
}
