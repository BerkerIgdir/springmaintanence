package com.example.maintanence.web.controller;

import com.example.maintanence.domain.Employee;
import com.example.maintanence.domain.enums.employee.Crew;
import com.example.maintanence.dto.EmployeeDto;
import com.example.maintanence.dto.MalfunctionDto;
import com.example.maintanence.mapper.EmployeeMapper;
import com.example.maintanence.service.crudservice.EmployeeService;
import com.example.maintanence.service.eventservice.EmployeeEventInterface;
import com.example.maintanence.web.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/api/employee")
@RequiredArgsConstructor
public class EmployeeController {

    public static Queue<Crew> requestQueue = new LinkedList<>();

    private final EmployeeService employeeService;

    private final EmployeeEventInterface employeeEventInterface;

    @GetMapping("/{id}")
    ResponseEntity<?> getById(@PathVariable("id") UUID id){ return new ResponseEntity<>(employeeService.getById(id), HttpStatus.OK); }

    @GetMapping
    ResponseEntity<?> getAll(){

        Page<EmployeeDto> employeeDtos = employeeService.getAll();

        if(employeeDtos.isEmpty())
            return new ResponseEntity<>("No Employee is found",HttpStatus.NOT_FOUND);


        return new ResponseEntity<>(employeeDtos,HttpStatus.OK) ;
    }


    @PostMapping("/register")
    ResponseEntity<?> newEmployee(@RequestBody Employee employee){

        return new ResponseEntity<>(employeeService.save(employee),HttpStatus.OK);
    }

    @PostMapping("/crashregister")
    ResponseEntity<?> newMalfunction(@RequestBody MalfunctionDto malfunctionDto){



        return new ResponseEntity<>("Crash register is successfully taken",HttpStatus.OK);
    }


    @GetMapping(value = "/availables")
    Flux<EmployeeDto> getAvaliables(){
        log.info("Request for available employees is taken");

//        Flux<EmployeeDto> flux = Flux.fromIterable(employeeService.getAvailables());
//
//        flux.subscribe(emp -> System.out.println(emp.getName()));

        return Flux.fromIterable(employeeService.getAvailables()).doOnNext(employeeDto -> log.info(employeeDto.getName() + ": " + employeeDto.getCrew().toString()));
    }



    //Düzeltilecek
    @GetMapping("/available/{type}")
    Mono<EmployeeDto> getAvailable(@PathVariable("type") String type){

        Crew crew = Enum.valueOf(Crew.class,type.toUpperCase());

        log.info(crew.toString());
        List<EmployeeDto> employeeDtos = employeeService.getAvailables()
                .parallelStream()
                .filter(emp -> emp.getCrew().toString().toUpperCase().equals(crew.toString().toUpperCase()))
                .collect(Collectors.toList());


        if(employeeDtos.isEmpty()){

            requestQueue.add(crew);

            throw new NotFoundException();
        }

        EmployeeDto employeeDto = employeeDtos.get(0);

        employeeEventInterface.issueTake(employeeDto.getId());

        return Mono.just(employeeDto).log("Employee is sent");

    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<?> deleteEmployee(@PathVariable("id") UUID id){

        employeeService.deleteById(id);

        return new ResponseEntity<>("Successfully deleted",HttpStatus.OK);
    }

}
