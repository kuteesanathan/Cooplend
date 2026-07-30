package com.tui.cooplend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class CooplendApplication {

    public static void main(String[] args) {

        SpringApplication.run(CooplendApplication.class, args);
    }

}
