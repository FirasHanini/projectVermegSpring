package com.example.test.serviceDemo;

import com.example.test.Model.Entity.Person;
import com.example.test.Model.Repository.PersonRepository;
import com.example.test.Model.converters.PersonRequestToPersonConverter;
import com.example.test.Model.request.PersonRequest;

import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@DataJpaTest
class PersonServiceTest {

    @Mock
    private PersonRepository testRepository;
    @InjectMocks
    private PersonService testService;




    @Nested
    public class TestAdd {
        @Test
        public void personService_AddPerson_ReturnPersonView() {

            PersonRequest personRequest = PersonRequest.builder()
                    .nom("nom")
                    .prenom("prenom")
                    .adress("tunis")
                    .Email("nomprenom@tunis.tunis")
                    .build();


            Person person = testService.add(personRequest);




            Mockito.verify(testRepository, Mockito.times(1)).save(Mockito.any());


            //Argument capture
            Mockito.verify(testRepository).save(Mockito.argThat(savedPerson ->
                            savedPerson.getNom().equals(personRequest.getNom()) &&
                            savedPerson.getPrenom().equals(personRequest.getPrenom())

            ));

        }
    }


    @Nested
    public class TestFindAll {
        @Test
        public void personService_FindAll_ReturnListPersonView() throws Exception {
            //ARRANGE
            PersonRequestToPersonConverter personRequestToPersonConverter = new PersonRequestToPersonConverter();
            PersonRequest personRequest = PersonRequest.builder()
                    .nom("nom")
                    .prenom("prenom")
                    .adress("tunis")
                    .Email("nomprenom@tunis.tunis")
                    .build();

            PersonRequest personRequest2 = PersonRequest.builder()
                    .nom("prenom")
                    .prenom("nom")
                    .adress("bizerte")
                    .Email("bizerteprenom@tunis.tunis")
                    .build();
            List<Person> listFound = new ArrayList<>();
            List<Person> listSaved = new ArrayList<>();

            Person person1 = new Person();
            Person person2 = new Person();
            person1 = personRequestToPersonConverter.apply(personRequest);
            person2 = personRequestToPersonConverter.apply(personRequest);
            listSaved.add(person1);
            listSaved.add(person2);
            when(testRepository.findAll()).thenReturn(listSaved);


            //ACT
            listFound = testService.findAll().get();

            //ASSERTION
            Mockito.verify(testRepository, Mockito.times(1)).findAll();
            Assertions.assertEquals(listFound, List.of(person1,person2));


        }

        @Test
        public void emptyDataBase_ReturnValiDationResultInvalid() throws Exception {
            //ARRANGE
            List<Person>resultRepository=new ArrayList<>();
            when(testRepository.findAll()).thenReturn(resultRepository);

            //ACT
            ValidationResult<List<Person>> result= testService.findAll();

            //ASSERTIONS

            assertFalse(result.isValid());


        }
    }


    @Nested
    public class TestFindById {

        @Test
        public void testFindById_PersonFound_ReturnPerson() throws Exception {
            long id = 1L;
            Person person = new Person();
            person.setId(id);

            when(testRepository.findById(id)).thenReturn(Optional.of(person));

            Person result = testService.findById(id).get();

            assertNotNull(result);
            assertEquals(id, result.getId());
        }

        @Test
        public void testFindById_PersonNotFound_ReturnNull() throws Exception {
            long id = 2L;


            when(testRepository.findById(id)).thenReturn(Optional.empty());

           ValidationResult<Person>result = testService.findById(id);

            assertFalse(result.isValid());
            Assertions.assertEquals(result.getError(),ErrorResponse.valueOf("Person not found",String.format("The requested ID %d does not exist in the database.",id)));

        }


    }



    @Nested
    public class TestDelete {

        @Test
        public void testDeletePerson_PersonFound() throws Exception {
           //ARRANGE
            long id = 1L;
            Person person = new Person();
            person.setId(id);


            when(testRepository.findById(id)).thenReturn(Optional.of(person));

            // ACT
            Long result = testService.delete(id).get();


            //ASSERT

            Mockito.verify(testRepository, Mockito.times(1)).deleteById(id);


            Assertions.assertEquals(id,result);
        }
        @Test
        public void testDeletePerson_PersonNotFound() throws Exception {
            //ARRANGE
            long id = 2L;

            when(testRepository.findById(id)).thenReturn(Optional.empty());

            // ACT
            ValidationResult<Long> result = testService.delete(id);


            //ASSERT

            Mockito.verify(testRepository, Mockito.never()).deleteById(id);


            Assertions.assertEquals(result.getError(),ErrorResponse.valueOf("Person not found",String.format("The requested ID %d does not exist in the database.",id)));

        }


    }
}

