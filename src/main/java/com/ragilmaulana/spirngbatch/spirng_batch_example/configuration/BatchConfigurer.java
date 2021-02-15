package com.ragilmaulana.spirngbatch.spirng_batch_example.configuration;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ragilmaulana.spirngbatch.spirng_batch_example.entity.Person;
import com.ragilmaulana.spirngbatch.spirng_batch_example.entity.PersonHistory;

@Configuration
public class BatchConfigurer extends DefaultBatchConfigurer{
	
	
	@Bean
	public Job startBatch(JobBuilderFactory jobBuilderFactory, Step step) {
		return jobBuilderFactory.get("personEfective")
		.incrementer(new RunIdIncrementer())
		.start(step)
		.build();
	}
	
	@Bean
	public Step step1(StepBuilderFactory builderFactory,
			ItemReader<Person> personReader,
			ItemWriter<PersonHistory> personWriter,
			ItemProcessor<Person,PersonHistory> personProcessor) {
		
		return builderFactory.get("step1")
				.<Person, PersonHistory>chunk(10000)
				.reader(personReader)
				.processor(personProcessor)
				.writer(personWriter)
				.build();
	}

}
