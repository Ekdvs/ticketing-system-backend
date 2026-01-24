package com.company.ticket_booking_backend;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class TicketBookingBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicketBookingBackendApplication.class, args);
	}

}
