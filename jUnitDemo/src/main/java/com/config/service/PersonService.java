package com.config.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.config.entity.Person;
import com.config.repository.PersonRepo;

@Service
public class PersonService {
    @Autowired
    private PersonRepo repo;

    public List<Person> getAllPerson() {
        return this.repo.findAll();
    }

    public PersonService(PersonRepo repo) {
        this.repo = repo;
    }
}
