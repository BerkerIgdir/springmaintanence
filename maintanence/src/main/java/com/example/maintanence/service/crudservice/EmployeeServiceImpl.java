package com.example.maintanence.service.crudservice;

import com.example.maintanence.domain.Employee;
import com.example.maintanence.domain.enums.employee.Crew;
import com.example.maintanence.domain.enums.employee.EmployeeStatus;
import com.example.maintanence.dto.EmployeeDto;
import com.example.maintanence.mapper.EmployeeMapper;
import com.example.maintanence.repos.EmployeeRepository;
import com.example.maintanence.service.EmployeeClient;
import com.example.maintanence.web.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private final EmployeeRepository employeeRepository;

    @Autowired
    private final EmployeeMapper employeeMapper;

    @Autowired
    private final EmployeeClient employeeClient;


    public static Queue<Crew> requestQueue = new LinkedList<>();


    @Override
    public Page<EmployeeDto> getByCrew(Crew crew, Pageable pageable) {

        Page<Employee> employees = employeeRepository.findByCrew(crew,pageable);

        List<EmployeeDto> dtoList = employees.stream()
                .map(employeeMapper::employeeToEmployeeDto)
                .collect(Collectors.toList());


        return new PageImpl<>(dtoList);
    }

    @Override
    public EmployeeDto getBySurname(String surname) {

        Employee employee = employeeRepository.findBySurname(surname).orElseThrow(NotFoundException::new);

        return employeeMapper.employeeToEmployeeDto(employee);
    }

    @Override
    public Set<EmployeeDto> getAvailables() {

        Set<EmployeeDto> employeeDtoSet = new HashSet<>();

        employeeRepository
                .findAll()
                .stream()
                .filter(emp -> emp.getStatus() == EmployeeStatus.AVAILABLE)
                .map(employeeMapper::employeeToEmployeeDto)
                .forEach(employeeDtoSet::add);



        return employeeDtoSet;
    }



    @Override
    public EmployeeDto getById(UUID id) {
        return employeeMapper.employeeToEmployeeDto(employeeRepository.findById(id).orElseThrow(NotFoundException::new));
    }


    @Override
    public Page<EmployeeDto> getAll() {

        List<Employee> employees = employeeRepository.findAll();

        List<EmployeeDto> employeeDtos = employees.stream().map(employeeMapper::employeeToEmployeeDto).collect(Collectors.toList());

        return new PageImpl<>(employeeDtos);
    }

    @Override
    public EmployeeDto save(Employee emp) {
        try {
            return employeeMapper.employeeToEmployeeDto(employeeRepository.save(emp));
        }
        catch (IllegalArgumentException e){
            System.out.println("Given entity is null");
            return null;
        }
    }

    @Override
    public void re_availableEmployee(UUID id) {


        if(!requestQueue.isEmpty() &&
                this.getAvailables().stream().filter(emp -> requestQueue.contains(emp.getCrew())).count() != 0){

            //parallel stream may not be faster than sequential stream because Set is not an ordered data structure!!!
            EmployeeDto employeeDto = getById(id);

            requestQueue.remove(employeeDto.getCrew());

            employeeClient.notifyGateway(employeeDto);
        }

    }

    @Override
    public void deleteById(UUID id) {

        try{
            employeeRepository.deleteById(id);
        }
        catch (IllegalArgumentException e){
            System.out.println("Given entity could not be found");
        }

    }
}
