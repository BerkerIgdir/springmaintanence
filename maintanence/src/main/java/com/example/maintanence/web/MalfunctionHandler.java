//package com.example.maintanence.web;
//
//import com.example.maintanence.dto.EmployeeDto;
//import com.example.maintanence.dto.MalfunctionDto;
//import com.example.maintanence.service.crudservice.EmployeeService;
//import com.example.maintanence.service.eventservice.EmployeeEventInterface;
//import lombok.*;
//
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.*;
//import java.util.stream.Collectors;
//
//
//@Getter
//@Setter
//@AllArgsConstructor
//@RequiredArgsConstructor
//public class MalfunctionHandler {
//
//    private Queue<MalfunctionDto> malfunctionDtos = new PriorityQueue<>();
//
//    @Autowired
//    private final EmployeeService employeeService;
//
//    @Autowired
//    private final EmployeeEventInterface employeeEventService;
//
//    private Set<EmployeeDto> availableEmployees =  employeeService.getAvailables().stream().collect(Collectors.toSet());
//
//    private void addMalfunction(MalfunctionDto malfunctionDto) {
//        malfunctionDtos.add(malfunctionDto);
//    }
//
//    private void removeMalfunction(MalfunctionDto malfunctionDto) {
//        malfunctionDtos.remove(malfunctionDto);
//    }
//
//    void onNewRegister(MalfunctionDto malfunctionDto) {
//
//        List<EmployeeDto> availableFitEmployees = availableEmployees.stream().filter(employeeDto -> employeeDto
//                .getCrew().toString().toUpperCase().equals(malfunctionDto.getMalfunctionType()))
//                .collect(Collectors.toList());
//
//        if (availableEmployees.isEmpty()) {
//            addMalfunction(malfunctionDto);
//        } else {
//
//            availableFitEmployees.remove(availableFitEmployees.get(0));
//
//            EmployeeDto theDto = employeeService.getById(availableFitEmployees.get(0).getId());
//
//            theDto.setMalfunctionId(malfunctionDto.getId());
//
//            employeeEventService.issueSolve(theDto.getId());
//
//        }
//
//
//    }
//
//    void update(UUID employeeId){
//
//        availableEmployees.add(employeeService.getById(employeeId));
//
//        if(!malfunctionDtos.isEmpty()){
//            onNewRegister(malfunctionDtos.remove());
//        }
//
//    }
//
//}