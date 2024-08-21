package com.example.test.serviceDemo;

import com.example.test.Model.Entity.Person;
import com.example.test.Model.converters.PersonRequestToPersonConverter;
import com.example.test.Model.request.PersonRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.test.Model.Repository.PersonRepository;

import java.util.List;
import java.util.Optional;
@AllArgsConstructor
@Service
public class PersonService {

  
    private final PersonRepository repository;

    private PersonRequestToPersonConverter converter;
    private PersonRequestToPersonConverter personRequestConverter;

    //Tests
    public ValidationResult<Person> findById(final Long id ) throws Exception {
        Optional<Person> optionalPerson= repository.findById(id);
        if (optionalPerson.isEmpty())

            return ValidationResult
                    .invalid(ErrorResponse.valueOf("Person not found",String.format("The requested ID %d does not exist in the database.",id)));

       return ValidationResult.valid(optionalPerson.get());
    }

    public ValidationResult<Long> delete(final Long id) throws Exception {
        try {
              if(!findById(id).isValid())
                return ValidationResult.invalid( ErrorResponse.valueOf("Person not found",String.format("The requested ID %d does not exist in the database.",id)));
            repository.deleteById(id);
            return  ValidationResult.valid(id);
        }
        catch (Exception exception)
        {
            return ValidationResult.invalid(ErrorResponse.valueOf(String.valueOf(exception.getCause()),exception.getMessage()));


        }
    }

    public Person add(PersonRequest personRequest){
        Person person;
        person= converter.apply(personRequest);
        repository.save(person);

        return person;


    }


    public ValidationResult<Person> update(PersonRequest personRequest, Long id) throws Exception {

        if(repository.findById(id).isEmpty())
            return ValidationResult.invalid( ErrorResponse.valueOf("Person not found",String.format("The requested ID %d does not exist in the database.",id)));
        Person person ;
        person = personRequestConverter.apply(personRequest);
        person.setId(id);
        repository.save(person);
        return ValidationResult.valid(person);

    }
     


        public ValidationResult<List<Person>> findAll() throws Exception {
            List<Person> result;
            result=repository.findAll();
            if(result.isEmpty())
                return ValidationResult.invalid( ErrorResponse.valueOf("Not Found","Empty dataBase"));

            return  ValidationResult.valid(result);
        }

    }
