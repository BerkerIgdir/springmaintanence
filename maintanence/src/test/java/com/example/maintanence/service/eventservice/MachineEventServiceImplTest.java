package com.example.maintanence.service.eventservice;

import com.example.maintanence.domain.Machine;
import com.example.maintanence.domain.enums.employee.Factory;
import com.example.maintanence.domain.enums.machines.MachineEvent;
import com.example.maintanence.domain.enums.machines.MachineStatus;
import com.example.maintanence.domain.enums.machines.MachineType;
import com.example.maintanence.repos.MachineRepository;
import com.example.maintanence.web.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.config.StateMachineFactory;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MachineEventServiceImplTest {

    @Autowired
    StateMachineFactory<MachineStatus, MachineEvent> stateMachineFactory;

    @Autowired
    MachineRepository machineRepository;

    @Autowired
    MachineEventService theService;

    Machine machine;

    @BeforeEach
    void setUp() {

        machine = Machine.builder()
                .machineType(MachineType.Packaging)
                .name("Paketleme")
                .machineStatus(MachineStatus.OFF)
                .factory(Factory.Devanlay)
                .build();

    }

    @Test
    void turnOn() {
        Machine savedMachine =  machineRepository.save(machine);
        theService.turnOn(savedMachine.getId());
       System.out.println(savedMachine.getMachineStatus());

    }

    @Test
    void turnOff() {
    }

    @Test
    void fix() {
        theService.fix(machine.getId());

    }

    @Test
    void crash() {
    }

    @Test
    void maintain() {
    }
}