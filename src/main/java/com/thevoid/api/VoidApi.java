package com.thevoid.api;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.thevoid.api")
@SpringBootApplication
public class VoidApi {
    public static void main(String[] args) {
        SpringApplication.run(VoidApi.class, args);
    }
}