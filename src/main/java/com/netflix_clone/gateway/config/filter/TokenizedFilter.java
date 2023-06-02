package com.netflix_clone.gateway.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix_clone.gateway.constant.Constant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DefaultDataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created on 2023-05-19
 * Project gateway
 */
@Component(value = "outbound-filter")
@DependsOn(value = "constant")
@Slf4j
public class TokenizedFilter extends AbstractGatewayFilterFactory<TokenizedFilter.Config> {
    private Pattern outboundPattern = Pattern.compile("/sign/\\.*", Pattern.DOTALL);

    public TokenizedFilter(BaseFilter baseFilter) {
        super(TokenizedFilter.Config.class);
    }


    @Override
    public GatewayFilter apply(Config config) {

        return ((exchange, chain) -> {

//            exchange.getResponse().beforeCommit(() -> {
//                ServerHttpRequest request = exchange.getRequest();
//                ServerHttpResponse response = exchange.getResponse();
//
//                log.warn("BEFORE COMMIT");
//
//
//                RequestPath path = request.getPath();
//                String pathValue = path.value();
//                HttpStatus status = response.getStatusCode();
//
//                List<HttpStatus> statuses = Arrays.asList(HttpStatus.OK, HttpStatus.PARTIAL_CONTENT);
//                if(statuses.contains(status) &&outboundPattern.matcher(pathValue).find()){
//                    ServerHttpResponseDecorator decoratedResponse  = this.decorateResponse(exchange, response);
//                    return chain.filter(exchange.mutate().response(decoratedResponse).build());
//                }
//                return chain.filter(exchange);
//            });

            return chain.filter(exchange);
        });
    }

    private ServerHttpResponseDecorator decorateResponse(ServerWebExchange exchange, ServerHttpResponse response){
        DataBufferFactory bufferFactory = exchange.getResponse().bufferFactory();
        return new ServerHttpResponseDecorator(response){
            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {

                if (body instanceof Flux) {
                    Flux<? extends DataBuffer> fluxBody = (Flux<? extends DataBuffer>) body;
                    return super.writeWith(fluxBody.buffer().map(dataBuffers -> {
                        DefaultDataBuffer joinedBuffers = new DefaultDataBufferFactory().join(dataBuffers);

                        byte[] content = new byte[joinedBuffers.readableByteCount()];

                        joinedBuffers.read(content);
                        String responseBody = new String(content, StandardCharsets.UTF_8);
                        try {

//                            ObjectMapper objectMapper = new ObjectMapper();
//                            objectMapper.
                            log.warn("RESPONSEBODY {}", responseBody);
                            response.getHeaders().set(Constant.TOKEN_NAME, "HELLO");
                                	/*
                                    ///////////////////////////////////////////////////
                                    // (byte[] type) content 원하는 양식으로 변경 후 재주입
                                    ///////////////////////////////////////////////////
                                    */
                            return bufferFactory.wrap(content);
                        } catch (Exception e) {
                            // data 수정 없이 return
                            return joinedBuffers;
                        }
                    }));
                }



                return super.writeWith(body);
            }
        };
    }


    public static class Config {

    }
}

