package com.maintanence.malfunctiongateway.web.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.handler.WebFluxResponseStatusExceptionHandler;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@Order(-1)
public class MyExceptionHandler extends WebFluxResponseStatusExceptionHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange serverWebExchange, Throwable throwable) {

        if(serverWebExchange instanceof NoAvailableEmployeeException){
            ((NoAvailableEmployeeException) serverWebExchange).getErrorMessage();

            log.info("CAGRILDI");

        }

        return Mono.error(throwable);
    }
}
