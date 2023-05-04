package com.netflix_clone.gateway;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.lang.reflect.Parameter;

/**
 * Created on 2023-05-04
 * Project gateway
 */
@Component
public class WakeUp {

    @EventListener(value = {ApplicationReadyEvent.class})
    public void message() {
        System.out.println("" +
                "   ______      __     _       __            __\n" +
                "  / ____/___ _/ /____| |     / /___ ___  __/ /\n" +
                " / / __/ __ `/ __/ _ \\ | /| / / __ `/ / / / / \n" +
                "/ /_/ / /_/ / /_/  __/ |/ |/ / /_/ / /_/ /_/  \n" +
                "\\____/\\__,_/\\__/\\___/|__/|__/\\__,_/\\__, (_)   \n" +
                "                                  /____/  is ready!");
    }
}
