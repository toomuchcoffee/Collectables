package de.toomuchcoffee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CollectablesApplication {

	public static void main(String[] args) {
		SpringApplication.run(CollectablesApplication.class, args);
	}
}
