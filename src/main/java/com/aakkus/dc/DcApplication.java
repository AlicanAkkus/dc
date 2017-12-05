package com.aakkus.dc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class DcApplication {

    public static void main(String[] args) {
        SpringApplication.run(DcApplication.class, args);
    }
}
