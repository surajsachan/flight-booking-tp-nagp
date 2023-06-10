package com.nagp.flightbooking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableCircuitBreaker
@EnableEurekaClient
@SpringBootApplication
public class FlightBookingApplication {
	public static void main(String[] args) {
		SpringApplication.run(FlightBookingApplication.class, args);
	}

}
