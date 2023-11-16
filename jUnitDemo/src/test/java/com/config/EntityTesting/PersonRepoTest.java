package com.config.EntityTesting;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.config.entity.Person;
import com.config.repository.PersonRepo;
import com.fasterxml.jackson.databind.node.BooleanNode;


@SpringBootTest
public class PersonRepoTest {
	
	@Autowired
	private PersonRepo personRepo;
	
	@Test
	void isPersonExistById() {
		Person person = new Person(1,"Piyush","Delhi");
		personRepo.save(person);
		Boolean actualResult = personRepo.isPersonExitsById(1);
		assertThat(actualResult).isTrue();
	}

}
