package com.netflix_clone.gateway.exceptions;

/**
 * Created on 2023-05-18
 * Project gateway
 */
public class CommonException extends Exception{
    public CommonException(BecauseOf reason){
        super(reason.getReason());
    }
}
