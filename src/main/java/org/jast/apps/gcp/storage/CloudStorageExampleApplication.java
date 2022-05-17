package org.jast.apps.gcp.storage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CloudStorageExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudStorageExampleApplication.class, args);
	}

}
