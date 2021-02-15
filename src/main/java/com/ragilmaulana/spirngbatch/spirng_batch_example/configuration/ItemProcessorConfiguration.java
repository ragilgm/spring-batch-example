package com.ragilmaulana.spirngbatch.spirng_batch_example.configuration;

import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ragilmaulana.spirngbatch.spirng_batch_example.entity.Person;
import com.ragilmaulana.spirngbatch.spirng_batch_example.entity.PersonHistory;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class ItemProcessorConfiguration {
	
	private static final Logger log = org.slf4j.LoggerFactory.getLogger(ItemProcessorConfiguration.class);
	
	private AtomicInteger count = new AtomicInteger();
	
	private ObjectMapper mapper  = new ObjectMapper();

	@Bean
	public ItemProcessor<Person, PersonHistory> itenProcessor(){
		return new ItemProcessor<Person, PersonHistory>() {
			@Override
			public PersonHistory process(Person person)throws Exception{
				log.info("processing data : "+person.getId()+ " record no : "+count);
				return mapper.convertValue(person, PersonHistory.class);
			}
		};
	}
	
	
	
	
}
