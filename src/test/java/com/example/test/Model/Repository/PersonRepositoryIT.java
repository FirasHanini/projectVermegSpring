package com.example.test.Model.Repository;

import com.example.test.Model.Entity.Person;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;


@DataJpaTest
public class PersonRepositoryIT {
    @Autowired
    private PersonRepository testRepository;

    @Nested
    public class TestFindAll {
        @Test
        public void saveAndFindAll_SaveAndReturnListOfPerson() {
            //ARRANGE
            Person p1 = Person.builder().nom("P1").prenom("Lp1").build();
            Person p2 = Person.builder().nom("P2").prenom("LP2").build();

            //Collections.aslist()
            testRepository.saveAll(List.of(p1,p2));


            //ACT
            List<Person> result = testRepository.findAll();


            //ASSERT
            Assertions.assertThat(result.size()).isEqualTo(2);
            // Assertions.assertThat(result.size()).isEqualTo(1); FAILS
            //test clear and flush
            //1 save test Atribut by atribut


    }
    }
    @Nested
    public class TestFindById {
            @Test
            public void longId_FindById_ReturnPersonView() {
                //ARRANGE
                Person person1 = Person.builder().nom("Nom")
                        .prenom("Prenom")
                        .adress("tunis")
                        .email("nomprenom@gmail.test")
                        .build();

                testRepository.save(person1);
                //ACT
                Optional<Person> optionalPerson = testRepository.findById(person1.getId());



                //ASSERT
                Assertions.assertThat(optionalPerson).isNotEmpty();
                Assertions.assertThat(optionalPerson.get()).isEqualTo(person1);



        }

    }
    @Nested
    public class TestDelete{
        @Test
        public void longId_DeleteById_DeletePerson() {
            // ARRANGE
            Person person = Person.builder().nom("Nom")
                    .prenom("Prenom")
                    .adress("tunis")
                    .email("nomprenom@gmail.test")
                    .build();
            testRepository.save(person);


            //ACT
            testRepository.deleteById(person.getId());

            //ASSERT
            Assertions.assertThat(testRepository.findById(person.getId()).orElse(null)).isEqualTo(null);

        }

    }
}

