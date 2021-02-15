package com.ragilmaulana.spirngbatch.spirng_batch_example;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class SpirngBatchExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpirngBatchExampleApplication.class, args);
	}

}
