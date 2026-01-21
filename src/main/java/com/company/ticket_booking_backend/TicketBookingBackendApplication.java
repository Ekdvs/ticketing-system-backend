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
		// Load .env file first
		Dotenv dotenv = null;
		try {
			dotenv = Dotenv.configure()
					.directory("./")
					.ignoreIfMissing()
					.load();
			System.out.println("✓ Environment variables loaded from .env file");
		} catch (Exception e) {
			System.err.println("⚠ Warning: Could not load .env file - " + e.getMessage());
		}

		// Create Spring application
		SpringApplication app = new SpringApplication(TicketBookingBackendApplication.class);

		// Add .env variables to Spring environment
		if (dotenv != null) {
			final Dotenv finalDotenv = dotenv;
			app.addInitializers(context -> {
				ConfigurableEnvironment env = context.getEnvironment();
				Map<String, Object> envMap = new HashMap<>();

				finalDotenv.entries().forEach(entry -> {
					envMap.put(entry.getKey(), entry.getValue());
				});

				env.getPropertySources().addFirst(new MapPropertySource("dotenvProperties", envMap));
				System.out.println("✓ Added " + envMap.size() + " properties to Spring environment");
			});
		}

		// Run the application
		app.run(args);
	}
}