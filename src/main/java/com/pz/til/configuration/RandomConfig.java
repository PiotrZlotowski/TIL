package com.pz.til.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration
public class RandomConfig {

    @Bean
    public Random createRandomBean() {
        return new Random(System.currentTimeMillis());
    }
}

