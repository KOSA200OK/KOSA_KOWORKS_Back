package com.my;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class KosaKoworksBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(KosaKoworksBackApplication.class, args);
	}

}
