package com.github.lsjunior.sbk8stest.service;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.github.lsjunior.sbk8stest.jpa.entity.Person;
import com.github.lsjunior.sbk8stest.jpa.repository.PersonRepository;
import com.github.lsjunior.sbk8stest.utils.SpringRepositoryHelper;

@Service
@Validated
public class PersonService {

  private PersonRepository personRepository;

  public PersonService() {
    super();
  }

  @Autowired
  public void setPersonRepository(PersonRepository personRepository) {
    this.personRepository = personRepository;
  }

  public Person get(Long id) {
    return this.personRepository.findById(id).orElse(null);
  }

  public Person save(@Valid Person e) {
    String email = e.getEmail();
    long count = this.personRepository.countByEmail(email);
    if (count > 0) {
      throw new IllegalArgumentException("E-mail already registered");
    }

    return this.personRepository.save(e);
  }

  public Person update(@Valid Person e) {
    Optional<Person> optional = this.personRepository.findById(e.getId());
    if (!optional.isPresent()) {
      throw new IllegalArgumentException("Person not found");
    }
    Person person = optional.get();
    person.setEmail(e.getEmail());
    person.setName(e.getName());
    person.setPhone(e.getPhone());
    person = this.personRepository.save(person);
    return person;
  }

  public boolean delete(Long id) {
    Optional<Person> optional = this.personRepository.findById(id);
    if (!optional.isPresent()) {
      return false;
    }
    Person person = optional.get();
    this.personRepository.delete(person);
    return true;
  }

  public Page<Person> list(String query, Pageable pageable) {
    Pageable pageableToUse = SpringRepositoryHelper.toSort(pageable, "name");
    Page<Person> page = this.personRepository.findByNameContaining(query, pageableToUse);
    return page;
  }

}
