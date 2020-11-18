package com.maintanence.malfunctiongateway.web;



import com.maintanence.malfunctiongateway.dto.EmployeeDto;
import com.maintanence.malfunctiongateway.dto.MalfunctionDto;
import com.maintanence.malfunctiongateway.service.MalfunctionRegister;
import com.maintanence.malfunctiongateway.web.exception.NoAvailableEmployeeException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;


@Slf4j
@RestController
@RequestMapping("/api/handler")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GateController {

    @Autowired
    final MalfunctionRegister malfunctionRegister;

    @PostMapping(value = "/newMalfunction")
    public ResponseEntity<?> getAvailableEmployees(@RequestBody MalfunctionDto newMalfunction) throws NoAvailableEmployeeException {

        try {
            malfunctionRegister.onCrashRegister(newMalfunction);
        }
        catch (NoAvailableEmployeeException ex){
            log.info("No available employee is found" + ex.getErrorMessage());
        }
        return new ResponseEntity<>("Registery is accepted", HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "/notify")
    public ResponseEntity<?> notify(@RequestBody EmployeeDto employeeDto){

        malfunctionRegister.employeeBecomesAvailable(employeeDto);


        return new ResponseEntity<>("Employee is registered successfully",HttpStatus.ACCEPTED);
    }

//    @ExceptionHandler(value = NoAvailableEmployeeException.class)
//    ResponseEntity<?> noEmployeeHandler(NoAvailableEmployeeException ex){
//
//        return new ResponseEntity<>("NOT AVAILABLE YET ",HttpStatus.NOT_FOUND);
//    }

}
