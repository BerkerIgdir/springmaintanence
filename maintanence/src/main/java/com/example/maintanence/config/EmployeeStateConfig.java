package com.example.maintanence.config;

import com.example.maintanence.domain.enums.employee.EmployeeEvent;
import com.example.maintanence.domain.enums.employee.EmployeeStatus;
import com.example.maintanence.domain.enums.machines.MachineEvent;
import com.example.maintanence.domain.enums.machines.MachineStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

import java.util.EnumSet;

@Configuration
@EnableStateMachineFactory(name = "eventFactory")
@Slf4j
public class EmployeeStateConfig extends StateMachineConfigurerAdapter<EmployeeStatus, EmployeeEvent> {
    @Override
    public void configure(StateMachineStateConfigurer<EmployeeStatus, EmployeeEvent> states) throws Exception {

        states.withStates().initial(EmployeeStatus.AVAILABLE)
                .states(EnumSet.allOf(EmployeeStatus.class))
                .end(EmployeeStatus.BUSY)
                .end(EmployeeStatus.LEAVE);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<EmployeeStatus, EmployeeEvent> transitions) throws Exception {
            transitions
                    .withExternal()
                    .source(EmployeeStatus.AVAILABLE).target(EmployeeStatus.BUSY).event(EmployeeEvent.ISSUE_TAKEN)
                    .and().withExternal()
                    .source(EmployeeStatus.BUSY).target(EmployeeStatus.AVAILABLE).event(EmployeeEvent.ISSUE_SOLVED)
                    .and().withExternal()
                    .source(EmployeeStatus.AVAILABLE).target(EmployeeStatus.LEAVE).event(EmployeeEvent.LEAVE_TAKEN);
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<EmployeeStatus, EmployeeEvent> config) throws Exception {

        StateMachineListenerAdapter<EmployeeStatus,EmployeeEvent> adapter = new StateMachineListenerAdapter<EmployeeStatus,EmployeeEvent>(){

            @Override
            public void stateChanged(State<EmployeeStatus,EmployeeEvent> from, State<EmployeeStatus,EmployeeEvent> to) {
                log.info(String.format("State DEGISTI bundan: %s buna: %s",from,to));
            }
        };
        config.withConfiguration().listener(adapter);
    }
}
