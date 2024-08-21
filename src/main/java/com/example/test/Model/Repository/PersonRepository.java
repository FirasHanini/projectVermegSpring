package com.example.test.Model.Repository;
import com.example.test.Model.Entity.Person;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person,Long> {



    @Override
    Optional<Person> findById(Long aLong);

    List<Person> findAll();

    void deleteById(Long id);

    <S extends Person> S save(S entity);












}
