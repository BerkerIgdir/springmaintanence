package com.example.maintanence.service.eventservice;

import com.example.maintanence.domain.Machine;
import com.example.maintanence.domain.enums.machines.MachineEvent;
import com.example.maintanence.domain.enums.machines.MachineStatus;
import com.example.maintanence.repos.MachineRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MachineEventServiceImpl implements MachineEventService{

    @Autowired
    private final StateMachineFactory<MachineStatus,MachineEvent> theFactory;

    @Autowired
    private final MachineRepository machineRepository;

    @Autowired
    private final MachineStateInterceptor machineStateInterceptor;
    @Override
    public StateMachine<MachineStatus, MachineEvent> turnOn(UUID machineId) {

        StateMachine<MachineStatus,MachineEvent> stateMachine = getStateMachineWithState(machineId);

        sendEvent(machineId,stateMachine,MachineEvent.TURNED_ON);

        return stateMachine;
    }

    @Override
    public StateMachine<MachineStatus, MachineEvent> turnOff(UUID machineId) {

        StateMachine<MachineStatus,MachineEvent> stateMachine = getStateMachineWithState(machineId);

        sendEvent(machineId,stateMachine,MachineEvent.TURNED_OFF);


        return stateMachine;
    }

    @Override
    public StateMachine<MachineStatus, MachineEvent> fix(UUID machineId) {

        StateMachine<MachineStatus,MachineEvent> stateMachine = getStateMachineWithState(machineId);

        sendEvent(machineId,stateMachine,MachineEvent.FIXED);


        return stateMachine;
    }

    @Override
    public StateMachine<MachineStatus, MachineEvent> crash(UUID machineId) {

        StateMachine<MachineStatus,MachineEvent> stateMachine = getStateMachineWithState(machineId);

        sendEvent(machineId,stateMachine,MachineEvent.CRASH);

        return stateMachine;
    }

    @Override
    public StateMachine<MachineStatus, MachineEvent> maintain(UUID machineId) {

        StateMachine<MachineStatus,MachineEvent> stateMachine = getStateMachineWithState(machineId);

        sendEvent(machineId,stateMachine,MachineEvent.MAINTANENCE);


        return stateMachine;
    }

    private void sendEvent(UUID machineId,StateMachine<MachineStatus,MachineEvent> sm,MachineEvent event){

        Message msg = MessageBuilder
                .withPayload(event)
                .setHeader("Machine Id",machineId)
                .build();

        sm.sendEvent(msg);

    }

    @Transactional
    private StateMachine<MachineStatus,MachineEvent> getStateMachineWithState(UUID id) {

        Machine machine = machineRepository.findById(id).orElseThrow(NoClassDefFoundError::new);

        StateMachine<MachineStatus, MachineEvent> stateMachine = theFactory.getStateMachine(id);

        stateMachine.stop();

        stateMachine.getStateMachineAccessor()
                .doWithAllRegions(sma ->{
                        sma.addStateMachineInterceptor(machineStateInterceptor);
                        sma.resetStateMachine(new DefaultStateMachineContext<>(
                                machine.getMachineStatus(),null,null ,null));
                        });
        stateMachine.start();

        return stateMachine;
    }



}
