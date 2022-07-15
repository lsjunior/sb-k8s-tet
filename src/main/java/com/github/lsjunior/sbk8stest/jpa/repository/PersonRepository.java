package com.github.lsjunior.sbk8stest.jpa.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.lsjunior.sbk8stest.jpa.entity.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

  long countByEmail(String email);

  Optional<Person> findByEmail(String email);

  Page<Person> findByNameContaining(String name, Pageable pageable);

}
