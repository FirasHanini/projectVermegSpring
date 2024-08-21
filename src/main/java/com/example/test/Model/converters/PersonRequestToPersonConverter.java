package com.example.test.Model.converters;


import com.example.test.Model.Entity.Person;
import com.example.test.Model.request.PersonRequest;
import org.springframework.stereotype.Component;
import java.util.function.Function;

@Component
public class PersonRequestToPersonConverter implements Function< PersonRequest,Person> {

    @Override
    public Person apply(PersonRequest personRequest) {
        return Person.builder()
                .nom(personRequest.getNom())
                .prenom(personRequest.getPrenom())
                .adress(personRequest.getAdress())
                .email(personRequest.getEmail())
                .build();

    }






}
