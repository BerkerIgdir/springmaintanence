package com.example.maintanence.config;

import com.example.maintanence.domain.enums.machines.MachineEvent;
import com.example.maintanence.domain.enums.machines.MachineStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

import java.util.EnumSet;


@Configuration
@Slf4j
@EnableStateMachineFactory(name = "machineFactory")
public class MachineStateConfig extends StateMachineConfigurerAdapter<MachineStatus, MachineEvent> {

    @Override
    public void configure(StateMachineStateConfigurer<MachineStatus, MachineEvent> states) throws Exception {
        states.withStates()
                .initial(MachineStatus.OFF)
                .states(EnumSet.allOf(MachineStatus.class))
                .end(MachineStatus.CRASHED)
                .end(MachineStatus.IN_MAINTENANCE)
                .end(MachineStatus.IN_SERVICE)
                .end(MachineStatus.OUT_OF_SERVICE);

    }

    @Override
    public void configure(StateMachineTransitionConfigurer<MachineStatus, MachineEvent> transitions) throws Exception {
        transitions.withExternal()
                .source(MachineStatus.OFF).target(MachineStatus.IN_SERVICE).event(MachineEvent.TURNED_ON)
                .and().withExternal()
                .source(MachineStatus.IN_SERVICE).target(MachineStatus.OUT_OF_SERVICE).event(MachineEvent.CRASH)
                .and().withExternal()
                .source(MachineStatus.CRASHED).target(MachineStatus.IN_SERVICE).event(MachineEvent.FIXED)
                .and().withExternal()
                .source(MachineStatus.IN_SERVICE).target(MachineStatus.IN_MAINTENANCE).event(MachineEvent.MAINTANENCE)
                .and().withExternal()
                .source(MachineStatus.IN_MAINTENANCE).target(MachineStatus.IN_SERVICE).event(MachineEvent.FIXED)
                .and().withExternal()
                .source(MachineStatus.IN_SERVICE).target(MachineStatus.OFF).event(MachineEvent.TURNED_OFF);
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<MachineStatus, MachineEvent> config) throws Exception {

        StateMachineListenerAdapter<MachineStatus,MachineEvent> adapter = new StateMachineListenerAdapter<MachineStatus,MachineEvent>(){

            @Override
            public void stateChanged(State<MachineStatus,MachineEvent> from, State<MachineStatus,MachineEvent> to) {
                log.info(String.format("State DEGISTI bundan: %s buna: %s",from,to));
            }
        };
        config.withConfiguration().listener(adapter);
    }
}
