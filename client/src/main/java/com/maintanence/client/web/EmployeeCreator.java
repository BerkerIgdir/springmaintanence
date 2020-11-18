//package com.maintanence.client.web;
//
//import com.maintanence.client.domain.Employee;
//import com.maintanence.client.domain.Malfunction;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.MediaType;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.scheduling.annotation.EnableAsync;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import org.springframework.web.reactive.function.client.WebClient;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//import java.util.Random;
//
//
//@Component
//@Slf4j
//@EnableAsync
//public class EmployeeCreator {
//
//    private WebClient webClient = WebClient.create("http://localhost:8082/api/employee");
//
//    private String[] malfunctionTypes = {"MECHANICAL","ELECTRICAL"};
//
//    private Random rand = new Random();
//
//    @Scheduled(fixedRate = 1000)
//    @Async
//    public void createMalfunctions(){
//
//        log.info("Request is being sent");
//
//        Mono<Employee> employeeMono =  Mono.just(Employee.builder()
//                .name("Berker")
//                .surname("dummy")
//                .crew(malfunctionTypes[rand.nextInt(2)])
//                .build());
//
//        Flux<Employee> employeeFlux = webClient.post().uri("/register")
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(employeeMono,Employee.class)
//                .retrieve()
//                .bodyToFlux(Employee.class)
//                .log("Sent");
//
//        employeeFlux.subscribe(malfunction -> malfunction.getName());
//    }
//
//
//
//
//}
