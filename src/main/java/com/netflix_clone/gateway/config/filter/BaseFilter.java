package com.netflix_clone.gateway.config.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.util.Set;

/**
 * Created on 2023-05-18
 * Project gateway
 */
@Component(value = "base-filter")
@Slf4j
public class BaseFilter {
    public void logger(ServerHttpRequest request){
        String path = request.getPath().toString();
        HttpMethod method = request.getMethod();
        MultiValueMap<String, HttpCookie> cookies = request.getCookies();
        MultiValueMap<String, String> queryStrings = request.getQueryParams();
        String referer  = request.getHeaders().getFirst("referer");

        log.warn("\n PATH :: {} \n METHOD :: {} \n referer :: {}", path, method.name(), referer);

        this.cookieLogger(cookies);
        this.queryStringLogger(queryStrings);
    }

    private void cookieLogger(MultiValueMap<String, HttpCookie> cookieJar) {
        if(!cookieJar.isEmpty()){
            log.warn("===================================== COOKIE =====================================");
            Set<String> keySet = cookieJar.keySet();
            keySet.forEach( key -> {
                log.warn("{} - {}", key, cookieJar.get(key));
            });
            log.warn("================================================================================");
        }
    }
    private void queryStringLogger(MultiValueMap<String, String> queryStrings) {
        if(!queryStrings.isEmpty()){
            log.warn("===================================== QUERY STRINGS =====================================");
            Set<String> keySet = queryStrings.keySet();
            keySet.forEach( key -> {
                log.warn("{} - {}", key, queryStrings.get(key));
            });
            log.warn("=========================================================================================");
        }
    }
}
