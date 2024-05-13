package com.example.alwaysSpring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class AlwaysSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlwaysSpringApplication.class, args);
    }

}
