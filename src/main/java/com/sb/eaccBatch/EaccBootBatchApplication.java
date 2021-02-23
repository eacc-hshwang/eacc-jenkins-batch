package com.sb.eaccBatch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableBatchProcessing
@SpringBootApplication
public class EaccBootBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(EaccBootBatchApplication.class, args);
	}

}
