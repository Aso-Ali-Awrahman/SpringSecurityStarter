package com.aso.springsecuritystarter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringSecurityStarterApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityStarterApplication.class, args);
    }

}
