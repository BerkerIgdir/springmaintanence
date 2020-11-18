package com.maintanence.malfunctiongateway.service;

import com.maintanence.malfunctiongateway.dto.EmployeeDto;
import com.maintanence.malfunctiongateway.dto.MalfunctionDto;
import com.maintanence.malfunctiongateway.web.exception.NoAvailableEmployeeException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.LinkedList;
import java.util.Queue;

@Slf4j
@Service
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MalfunctionRegister {

    Queue<MalfunctionDto> malfunctionsQueue = new LinkedList<>();

    final String EMPLOYEE_URI = "http://localhost:8082/api/employee";

    final WebClient webClient = WebClient.create(EMPLOYEE_URI);

    public void onCrashRegister(MalfunctionDto newMalfunction)  {

        Mono<EmployeeDto> availableEmployee = webClient
                .get()
                .uri("/available/{type}", newMalfunction.getMalfunctionType())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> {
                    malfunctionsQueue.add(newMalfunction);
                    log.info("Crash is added to the Queue " + malfunctionsQueue.size());
                return Mono.error(new NoAvailableEmployeeException(clientResponse.statusCode().toString()));
                })
                .bodyToMono(EmployeeDto.class)
                .onErrorResume(e ->{
                    if(e instanceof NoAvailableEmployeeException){
                        log.info("NO AVAILABLE EMPLOYEE!!");
                    }
                    else{
                        log.info("SOMETHING UNEXPECTED HAPPENED");
                    }
                    return Mono.just(new EmployeeDto());
                })
                .filter(employeeDto -> employeeDto.getId() != null)
                .log();

        availableEmployee
                .subscribe(employeeDto -> {
                    employeeDto.setCrashId(newMalfunction.getId());
                });
    }

    public void employeeBecomesAvailable(EmployeeDto employeeDto){

        employeeDto.setCrashId(malfunctionsQueue.remove().getId());

    }

}