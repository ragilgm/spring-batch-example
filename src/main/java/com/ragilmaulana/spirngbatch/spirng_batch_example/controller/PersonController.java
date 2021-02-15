package com.ragilmaulana.spirngbatch.spirng_batch_example.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ragilmaulana.spirngbatch.spirng_batch_example.entity.Person;
import com.ragilmaulana.spirngbatch.spirng_batch_example.repo.PersonRepo;

@RestController
public class PersonController {
	
	@Autowired
	private PersonRepo personRepo;
	
	private final Logger log = LoggerFactory.getLogger(PersonController.class);
	
	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
	private Job job;

	
	@PostMapping("/")
	public void addPerson() {
		
		List<Person> personList = new ArrayList<>();
		for (int i = 0; i < 1000; i++) {
			
			Person person = new Person();
			person.setFullname("test");
			person.setPhoneNumber("02323");
			person.setStatus("inProgess");
			person.setCreatedDate(new Date());
			
			personList.add(person);
		}
		
		try {
			personRepo.saveAll(personList);
		} catch (Exception e) {
			log.info(e.getMessage(),e);
		}
		
	}
	
	@GetMapping("/")
	public void processBatch() {
		
		JobParameters jobParameter = new JobParametersBuilder()
				.addLong("time", System.currentTimeMillis()).toJobParameters();
		
		try {
			jobLauncher.run(job, jobParameter);
		} catch (JobExecutionAlreadyRunningException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JobRestartException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JobInstanceAlreadyCompleteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JobParametersInvalidException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
}
