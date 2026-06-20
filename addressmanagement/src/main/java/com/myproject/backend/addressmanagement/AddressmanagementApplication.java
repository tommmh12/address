package com.myproject.backend.addressmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@SpringBootApplication
public class AddressmanagementApplication {

	public static void main(String[] args) {
		loadDotenvIntoSystemProperties();
		SpringApplication.run(AddressmanagementApplication.class, args);
	}

	private static void loadDotenvIntoSystemProperties() {
		for (Path dotenvPath : resolveDotenvCandidates()) {
			if (Files.exists(dotenvPath)) {
				loadDotenvFile(dotenvPath);
				break;
			}
		}
	}

	private static List<Path> resolveDotenvCandidates() {
		Path workingDirectory = Path.of("").toAbsolutePath();
		return List.of(
				workingDirectory.resolve(".env"),
				workingDirectory.resolve("addressmanagement").resolve(".env"),
				workingDirectory.getParent() != null ? workingDirectory.getParent().resolve(".env") : workingDirectory.resolve(".env")
		);
	}

	private static void loadDotenvFile(Path dotenvPath) {
		try {
			for (String line : Files.readAllLines(dotenvPath)) {
				String trimmed = line.trim();
				if (trimmed.isEmpty() || trimmed.startsWith("#") || !trimmed.contains("=")) {
					continue;
				}

				int separatorIndex = trimmed.indexOf('=');
				String key = trimmed.substring(0, separatorIndex).trim();
				String value = trimmed.substring(separatorIndex + 1).trim();
				if (value.length() >= 2 && value.startsWith("\"") && value.endsWith("\"")) {
					value = value.substring(1, value.length() - 1);
				}

				applyIfAbsent(key, value);
			}
		} catch (IOException exception) {
			throw new IllegalStateException("Khong the doc file .env tai " + dotenvPath, exception);
		}
	}

	private static void applyIfAbsent(String key, String value) {
		String systemValue = System.getProperty(key);
		String environmentValue = System.getenv(key);
		if (isBlank(systemValue) && isBlank(environmentValue) && !isBlank(value)) {
			System.setProperty(key, value);
		}
	}

	private static boolean isBlank(String value) {
		return value == null || value.isBlank();
	}
}
