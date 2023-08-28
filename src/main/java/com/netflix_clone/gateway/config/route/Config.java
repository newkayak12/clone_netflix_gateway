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
//               BOARD
        return builder.routes()
                .route(
                        predicate -> predicate.path(
                                              "/api/v1/banner/**",
                                                        "/api/v1/comment/**",
                                                        "/api/v1/cs/**",
                                                        "/api/v1/faq/**",
                                                        "/api/v1/report/**",
                                                        "/api/v1/notice/**"
                                                   )
                                .filters(gatewayFilterSpec -> gatewayFilterSpec.filter(authorizationTokenFilter.apply(new AuthorizationTokenFilter.Config())))
                                .uri("lb://NETFLIX-CLONE-BOARD-SERVICE")
                )
//                BOARD
//
//                USER
                .route(
                        predicate -> predicate.path(
                                           "/api/v1/user/**",
                                                     "/api/v1/profile/**",
                                                     "/api/v1/ticket/**",
                                                     "/api/v1/user/**",
                                                     "/api/v1/pay/**"
                                                   )
                                .filters(gatewayFilterSpec -> gatewayFilterSpec.filter(authorizationTokenFilter.apply(new AuthorizationTokenFilter.Config())))
                                .uri("lb://NETFLIX-CLONE-USER-SERVICE")
                )
//                USER
//
//                MOVIE
                .route(
                        predicate -> predicate.path(
                                "/api/v1/watched/**",
                                          "/api/v1/person/**",
                                          "/api/v1/favorite/**",
                                          "/api/v1/contents/**",
                                          "/api/v1/category/**"
                                )
                                .filters(gatewayFilterSpec -> gatewayFilterSpec.filter(authorizationTokenFilter.apply(new AuthorizationTokenFilter.Config())))
                                .uri("lb://NETFLIX-CLONE-MOVIE-SERVICE")
                )
                .route(
                        predicate -> predicate.path("/api/v1/file/**")
                                              .uri("lb://NETFLIX-CLONE-FILE-SERVICE")
                )
//                MOVIE
                .build();
    }
}
