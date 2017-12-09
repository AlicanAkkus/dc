package com.aakkus.dc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class PreventDoubleClickApplication {

    public static void main(String[] args) {
        SpringApplication.run(PreventDoubleClickApplication.class, args);
    }
}
