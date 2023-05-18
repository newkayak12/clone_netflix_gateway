package com.netflix_clone.gateway.config.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Created on 2023-05-18
 * Project gateway
 */
@Component(value = "inbound-filter")
@DependsOn(value = "constant")
@Slf4j

public class AuthorizationTokenFilter extends AbstractGatewayFilterFactory<AuthorizationTokenFilter.Config> {
    private BaseFilter baseFilter;

    public AuthorizationTokenFilter(BaseFilter baseFilter) {
        super(Config.class);
        this.baseFilter = baseFilter;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            baseFilter.logger(request);

            RequestPath path = request.getPath();
            String pathValue = path.value();
            log.warn("PATHVALUE {}", pathValue);


            HttpHeaders headers = request.getHeaders();

//            if(!headers.containsKey(Constant.TOKEN_NAME)) return handlingUnAuthorization(exchange);
//            if(request.getHeaders().isEmpty())  return handlingUnAuthorization(exchange);
//            String token = request.getHeaders().get(Constant.TOKEN_NAME).stream().findAny().orElseGet(() -> null);
//            if(Objects.isNull(token)) return handlingUnAuthorization(exchange);

            return chain.filter(exchange);
        });
    }

    private Mono<Void> handlingUnAuthorization(ServerWebExchange exchange){
        ServerHttpResponse response =  exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }


    public static class Config {

    }
}
