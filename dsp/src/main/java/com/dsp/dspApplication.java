package com.dsp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class dspApplication {

	public static void main(String[] args) {
		SpringApplication.run(dspApplication.class, args);
	}

}
