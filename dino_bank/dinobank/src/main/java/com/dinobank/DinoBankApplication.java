package com.dinobank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DinoBankApplication {
	public static void main(String[] args) {
		SpringApplication.run(DinoBankApplication.class, args);
	}
}

// serverdan önce spring boot uygulamasını başlatmak gerekiyor. 8081 portunda backendi başlatıyor.