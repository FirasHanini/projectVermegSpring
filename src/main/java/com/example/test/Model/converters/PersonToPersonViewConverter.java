package com.example.test.Model.converters;

import com.example.test.Model.Entity.Person;
import com.example.test.Model.view.PersonView;
import org.springframework.stereotype.Component;


import java.util.function.Function;



@Component
public class PersonToPersonViewConverter implements Function<Person,PersonView>{



    @Override
    public PersonView apply(Person person) {
     return    PersonView.builder()
                .id(person.getId())
                .nom(person.getNom())
                .prenom(person.getPrenom())
                .adress(person.getAdress())
                .Email(person.getEmail())
                .build();

    }



}

