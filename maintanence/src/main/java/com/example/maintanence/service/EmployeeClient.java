package com.example.maintanence.service;

import com.example.maintanence.dto.EmployeeDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeClient {

    final String MALFUNCTION_GATEWAY_URI = "http://localhost:8089/api/handler";

    WebClient webClient = WebClient.create(MALFUNCTION_GATEWAY_URI);

    public Mono<ResponseEntity> notifyGateway(EmployeeDto employeeDto){

        return webClient.post().uri("/notify")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(employeeDto))
                .retrieve()
                .bodyToMono(ResponseEntity.class);

    }

}
