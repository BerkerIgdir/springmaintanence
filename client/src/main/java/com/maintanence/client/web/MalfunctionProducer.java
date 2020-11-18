package com.maintanence.client.web;

import com.maintanence.client.domain.Malfunction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.config.FixedRateTask;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Random;
import java.util.UUID;

@Component
@Slf4j
@EnableAsync
public class MalfunctionProducer {

    private WebClient webClient = WebClient.create("http://localhost:8088/api/v1");

    private String[] malfunctionTypes = {"MECHANICAL","ELECTRICAL"};

    private Random rand = new Random();

    @Scheduled(fixedRate = 1000)
    @Async
    public void createMalfunctions(){

    log.info("Request is being sent");

      Mono<Malfunction> malfunctionMono =  Mono.just(Malfunction.builder()
              .machineId(UUID.randomUUID())
              .description("RANDOM GENERATED")
              .malfunctionType(malfunctionTypes[rand.nextInt(2)])
              .build());

       Flux<Malfunction> malfunctionFlux = webClient.post().uri("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .body(malfunctionMono,Malfunction.class)
                .retrieve()
                .bodyToFlux(Malfunction.class)
                .log("Sent");

       malfunctionFlux.subscribe(malfunction -> malfunction.getDescription());
    }



}
