package com.maintanence.gateway.configure;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LocalRouteHandler {

    @Bean
    public RouteLocator localHostRoutes(RouteLocatorBuilder builder){

        return builder.routes()
                .route(r -> r.path("/api/employee*","/api/employee/*")
                        .uri("http://localhost:8082")
                        .id("employee"))
                .route(r -> r.path("/api/machine*","api/machine/*")
                        .uri("http://localhost:8082")
                        .id("machine"))
                        .build();
    }

}
