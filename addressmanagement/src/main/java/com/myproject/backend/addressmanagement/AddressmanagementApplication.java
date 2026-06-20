package com.myproject.backend.addressmanagement;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AddressmanagementApplication {

	public static void main(String[] args) {
		loadDotenvIntoSystemProperties();
		SpringApplication.run(AddressmanagementApplication.class, args);
	}

	private static void loadDotenvIntoSystemProperties() {
		Dotenv dotenv = Dotenv.configure()
				.ignoreIfMalformed()
				.ignoreIfMissing()
				.load();

		applyIfAbsent(dotenv, "DB_URL");
		applyIfAbsent(dotenv, "DB_USERNAME");
		applyIfAbsent(dotenv, "DB_PASSWORD");
	}

	private static void applyIfAbsent(Dotenv dotenv, String key) {
		String systemValue = System.getProperty(key);
		String environmentValue = System.getenv(key);
		if (isBlank(systemValue) && isBlank(environmentValue)) {
			String dotenvValue = dotenv.get(key);
			if (!isBlank(dotenvValue)) {
				System.setProperty(key, dotenvValue);
			}
		}
	}

	private static boolean isBlank(String value) {
		return value == null || value.isBlank();
	}
}
