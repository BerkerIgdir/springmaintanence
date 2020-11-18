package com.example.maintanence.service.eventservice;

import com.example.maintanence.domain.enums.employee.EmployeeEvent;
import com.example.maintanence.domain.enums.employee.EmployeeStatus;
import com.example.maintanence.dto.MalfunctionDto;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;

import java.util.UUID;

public interface EmployeeEventInterface {

    StateMachine<EmployeeStatus, EmployeeEvent> issueTake(UUID employeeId);
    StateMachine<EmployeeStatus, EmployeeEvent> issueSolve(UUID employeeId);
    StateMachine<EmployeeStatus, EmployeeEvent> leaveTake(UUID employeeId);

}
