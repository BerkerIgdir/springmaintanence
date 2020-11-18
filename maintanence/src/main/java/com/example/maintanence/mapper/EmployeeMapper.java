package com.example.maintanence.mapper;

import com.example.maintanence.domain.Employee;
import com.example.maintanence.dto.EmployeeDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    EmployeeDto employeeToEmployeeDto(Employee employee);

    Employee employeeToEmployeeDto(EmployeeDto employeeDto);
}
