package com.example.maintanence.service.eventservice;

import com.example.maintanence.domain.Employee;
import com.example.maintanence.domain.Machine;
import com.example.maintanence.domain.enums.employee.EmployeeEvent;
import com.example.maintanence.domain.enums.employee.EmployeeStatus;
import com.example.maintanence.domain.enums.machines.MachineEvent;
import com.example.maintanence.domain.enums.machines.MachineStatus;
import com.example.maintanence.repos.EmployeeRepository;
import com.example.maintanence.repos.MachineRepository;
import com.example.maintanence.web.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class MachineStateInterceptor extends StateMachineInterceptorAdapter<MachineStatus, MachineEvent>  {

    private final MachineRepository machineRepository;

    @Override
    public void preStateChange(State<MachineStatus, MachineEvent> state, Message<MachineEvent> message, Transition<MachineStatus, MachineEvent>  transition, StateMachine<MachineStatus, MachineEvent> stateMachine) {

        log.info("Interceptor cagrildi");
        Optional.ofNullable(message).ifPresent(msg -> {
            Optional.ofNullable(UUID.class.cast(msg.getHeaders().getOrDefault("Machine Id",UUID.randomUUID())))
                    .ifPresent(empId ->{
                        Machine machine = machineRepository.findById(empId).orElseThrow(NotFoundException::new);
                        machine.setMachineStatus(state.getId());
                        machineRepository.save(machine);
                    });
        });
        log.info("Interceptor cagrildi");
    }
}
