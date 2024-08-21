package com.example.test.converters;


import com.example.test.Model.Entity.Person;
import com.example.test.Model.converters.PersonRequestToPersonConverter;
import com.example.test.Model.converters.PersonToPersonViewConverter;
import com.example.test.Model.request.PersonRequest;

import com.example.test.Model.view.PersonView;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PersonRequestToPersonConverterTest {

    @Test
    public void personRequestPerson_Apply_ReturnPerson()
    {
        //ARRANGE
        Person person = new Person();
        PersonRequestToPersonConverter personRequestToPersonConverter=new PersonRequestToPersonConverter();
        PersonRequest personRequest=PersonRequest.builder()
                .nom("Test")
                .prenom("benTest")
                .adress("tunis")
                .Email("testbentest@gmail.test")
                .build();

        //ACT
        person=personRequestToPersonConverter.apply(personRequest);


        //ASSERT
        Assertions.assertEquals(personRequest.getNom(),person.getNom());
        Assertions.assertEquals(personRequest.getPrenom(),person.getPrenom());
        Assertions.assertEquals(personRequest.getAdress(),person.getAdress());
        Assertions.assertEquals(personRequest.getEmail(),person.getEmail());


    }


    @Test
    public void personPersonView_Apply_ReturnPersonView()
    {
        //ARRANGE
        Person person=Person.builder()
                .nom("Test")
                .prenom("benTest")
                .adress("tunis")
                .email("testbentest@gmail.test")
                .build();
        PersonView personView=new PersonView();
        PersonToPersonViewConverter personToPersonViewConverter=new PersonToPersonViewConverter();

        //ACT
        personView=personToPersonViewConverter.apply(person);

        //ASSERT
        Assertions.assertEquals(personView.getNom(),person.getNom());
        Assertions.assertEquals(personView.getPrenom(),person.getPrenom());
        Assertions.assertEquals(personView.getAdress(),person.getAdress());
        Assertions.assertEquals(personView.getEmail(),person.getEmail());


    }


}
