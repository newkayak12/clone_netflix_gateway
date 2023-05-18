package com.netflix_clone.gateway.config.route;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(value = "route")
public class Config {

    @Bean
    public RouteLocator gateWayRoute(RouteLocatorBuilder builder){
        return builder.routes()
                .route(
                        predicate -> predicate.path("/api/v1/board/**")
                                              .uri("lb://NETFLIX-CLONE-BOARD-SERVICE")
                )
                .route(
                        predicate -> predicate.path("/api/v1/user/**")
                                .uri("lb://NETFLIX-CLONE-USER-SERVICE")
                )
                .route(
                        predicate -> predicate.path("/api/v1/movie/**")
                                .uri("lb://NETFLIX-CLONE-USER-SERVICE")
                )
                .build();
    }
}
//    route(r -> r.path("/first-service/**")
//        .uri("http://localhost:8081"))
//        //second-service
//        .route(r -> r.path("/second-service/**")
//        .filters(f -> f.addResponseHeader("second-request", "second-request-header")
//        .addResponseHeader("second-response", "second-response-header"))
//        .uri("http://localhost:8082"))
//        .build();
