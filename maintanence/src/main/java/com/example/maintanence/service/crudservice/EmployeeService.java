package com.example.maintanence.service.crudservice;

import com.example.maintanence.domain.Employee;
import com.example.maintanence.domain.enums.employee.Crew;
import com.example.maintanence.dto.EmployeeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;

public interface EmployeeService {

    Page<EmployeeDto> getByCrew(Crew crew, Pageable pageable);

    EmployeeDto getBySurname(String surname);

    Set<EmployeeDto> getAvailables();

    EmployeeDto getById(UUID id);

    Page<EmployeeDto> getAll();

    EmployeeDto save(Employee dto);

    void re_availableEmployee(UUID id);

    void deleteById(UUID id);

}
