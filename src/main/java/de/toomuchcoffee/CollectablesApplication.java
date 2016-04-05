package de.toomuchcoffee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
public class CollectablesApplication {

	public static void main(String[] args) {
		SpringApplication.run(CollectablesApplication.class, args);
		try {
			System.out.println(InetAddress.getLocalHost().getHostName());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
}
