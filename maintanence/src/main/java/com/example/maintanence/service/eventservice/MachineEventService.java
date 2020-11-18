package com.example.maintanence.service.eventservice;

import com.example.maintanence.domain.Machine;
import com.example.maintanence.domain.enums.machines.MachineEvent;
import com.example.maintanence.domain.enums.machines.MachineStatus;
import org.springframework.statemachine.StateMachine;

import java.util.UUID;

public interface MachineEventService {

    StateMachine<MachineStatus, MachineEvent> turnOn(UUID machineId);

    StateMachine<MachineStatus, MachineEvent> turnOff(UUID machineId);

    StateMachine<MachineStatus, MachineEvent> fix(UUID machineId);

    StateMachine<MachineStatus, MachineEvent> crash(UUID machineId);

    StateMachine<MachineStatus, MachineEvent> maintain(UUID machineId);
}
