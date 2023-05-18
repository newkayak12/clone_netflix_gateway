package com.netflix_clone.gateway.exceptions;


import lombok.Getter;

@Getter
public enum BecauseOf {
    INVALID_TOKEN("잘못된 토큰입니다.");

    private String reason;
    BecauseOf(String reason){
        this.reason = reason;
    }
}
