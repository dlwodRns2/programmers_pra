package com.example.weather_client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class WeatherClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatherClientApplication.class, args);
	}

}
