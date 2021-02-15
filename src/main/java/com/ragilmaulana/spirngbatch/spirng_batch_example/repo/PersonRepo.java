package com.ragilmaulana.spirngbatch.spirng_batch_example.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ragilmaulana.spirngbatch.spirng_batch_example.entity.Person;

@Repository
public interface PersonRepo extends JpaRepository<Person, Integer> {

}
