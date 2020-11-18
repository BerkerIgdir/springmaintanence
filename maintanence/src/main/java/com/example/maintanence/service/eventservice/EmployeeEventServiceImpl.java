package com.example.maintanence.service.eventservice;

import com.example.maintanence.domain.Machine;
import com.example.maintanence.domain.enums.employee.Crew;
import com.example.maintanence.domain.enums.employee.EmployeeEvent;
import com.example.maintanence.domain.enums.employee.EmployeeStatus;
import com.example.maintanence.domain.enums.machines.MachineEvent;
import com.example.maintanence.domain.enums.machines.MachineStatus;
import com.example.maintanence.dto.MalfunctionDto;
import com.example.maintanence.repos.EmployeeRepository;
import com.example.maintanence.service.crudservice.EmployeeService;
import com.example.maintanence.web.exception.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;




//Actionlari OLUSTUR!

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class EmployeeEventServiceImpl implements EmployeeEventInterface {


    @Autowired
    private final EmployeeStateInterceptor employeeStateInterceptor;

    @Autowired
    private final EmployeeRepository employeeRepository;

    @Autowired
    private final EmployeeService employeeService;

    @Autowired
    private final StateMachineFactory<EmployeeStatus,EmployeeEvent> stateMachineFactory;

    @Override
    public StateMachine<EmployeeStatus, EmployeeEvent> issueTake(UUID employeeId) {

        var stateMachine = getStateMachineWithState(employeeId);

        sendEvent(employeeId,stateMachine,EmployeeEvent.ISSUE_TAKEN);

        return stateMachine;
    }

    @Override
    public StateMachine<EmployeeStatus, EmployeeEvent> issueSolve(UUID employeeId) {

        var stateMachine = getStateMachineWithState(employeeId);

        sendEvent(employeeId,stateMachine,EmployeeEvent.ISSUE_SOLVED);

        employeeService.re_availableEmployee(employeeId);
        log.info("ISSUE SOLVED");

        return stateMachine;
    }

    @Override
    public StateMachine<EmployeeStatus, EmployeeEvent> leaveTake(UUID employeeId) {

        var stateMachine = getStateMachineWithState(employeeId);

        sendEvent(employeeId,stateMachine,EmployeeEvent.LEAVE_TAKEN);

        return stateMachine;
    }

    private void sendEvent(UUID employeeId,StateMachine<EmployeeStatus,EmployeeEvent> sm,EmployeeEvent event){

        Message msg = MessageBuilder
                .withPayload(event)
                .setHeader("Employee Id",employeeId)
                .build();

        sm.sendEvent(msg);

    }

    @Transactional
    private StateMachine<EmployeeStatus, EmployeeEvent> getStateMachineWithState(UUID id) {

        var employee = employeeRepository.findById(id).orElseThrow(NotFoundException::new);

        var stateMachine = stateMachineFactory.getStateMachine(id);

        stateMachine.stop();

        stateMachine.getStateMachineAccessor().
                doWithAllRegions(sma ->{
                    sma.addStateMachineInterceptor(employeeStateInterceptor);
                    sma.resetStateMachine(new DefaultStateMachineContext<>(
                                employee.getStatus(),null,null ,null));
                        });
        stateMachine.start();

        return stateMachine;
    }


}
