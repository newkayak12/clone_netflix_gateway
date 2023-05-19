package com.netflix_clone.gateway.config.route;

import com.netflix_clone.gateway.config.filter.AuthorizationTokenFilter;
import com.netflix_clone.gateway.config.filter.TokenizedFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(value = "route")
@RequiredArgsConstructor
public class Config {
    private final AuthorizationTokenFilter authorizationTokenFilter;
    private final TokenizedFilter tokenizedFilter;

    @Bean
    public RouteLocator gateWayRoute(RouteLocatorBuilder builder){
        return builder.routes()
                .route(
                        predicate -> predicate.path("/api/v1/board/**")
                                .filters(gatewayFilterSpec -> gatewayFilterSpec.filter(authorizationTokenFilter.apply(new AuthorizationTokenFilter.Config())))
                                .uri("lb://NETFLIX-CLONE-BOARD-SERVICE")

                )
                .route(
                        predicate -> predicate.path("/api/v1/user/**")
                                .filters(gatewayFilterSpec -> {
                                    gatewayFilterSpec.filter(authorizationTokenFilter.apply(new AuthorizationTokenFilter.Config()));
                                    gatewayFilterSpec.filter(tokenizedFilter.apply(new TokenizedFilter.Config()));
                                    return  gatewayFilterSpec;
                                })
                                .uri("lb://NETFLIX-CLONE-USER-SERVICE")
                )
                .route(
                        predicate -> predicate.path("/api/v1/movie/**")
                                .filters(gatewayFilterSpec -> gatewayFilterSpec.filter(authorizationTokenFilter.apply(new AuthorizationTokenFilter.Config())))
                                .uri("lb://NETFLIX-CLONE-USER-SERVICE")
                )

                .route(
                        predicate -> predicate.path("/api/v1/file/**")
                                              .uri("lb://NETFLIX-CLONE-FILE-SERVICE")
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
