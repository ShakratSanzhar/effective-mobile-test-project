package com.example.effectivemobiletestproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class EffectiveMobileTestProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(EffectiveMobileTestProjectApplication.class, args);
    }

}
