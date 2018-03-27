package com.pz.til;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebFlux
public class TilApplication {

	public static void main(String[] args) {
		SpringApplication.run(TilApplication.class, args);
	}
}
