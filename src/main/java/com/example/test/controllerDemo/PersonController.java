package com.example.test.controllerDemo;


import com.example.test.Model.Entity.Person;
import com.example.test.Model.converters.PersonToPersonViewConverter;
import com.example.test.Model.request.PersonRequest;
import com.example.test.Model.view.PersonView;
import com.example.test.serviceDemo.PersonService;
import com.example.test.serviceDemo.ValidationResult;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value =api.Internal.person)

public class PersonController {


    private PersonService service;
    private  PersonToPersonViewConverter converter;




    @GetMapping(value= api.Internal.Person.FIND)
    public ResponseEntity findById(@PathVariable final  Long id) throws Exception {
        ValidationResult<Person> personValidation=service.findById(id);
       if(!personValidation.isValid()) {
           return  ResponseEntity
                   .status( HttpStatus.NOT_FOUND)
                   .body(personValidation.getError());
        }

        return ResponseEntity.ok(converter.apply(personValidation.get()));
    }


    @DeleteMapping (value=api.Internal.Person.DELETE)
    public ResponseEntity delete(@PathVariable Long id) throws Exception {
        ValidationResult<Long> deleteValidation = service.delete(id);
        if(!deleteValidation.isValid())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(deleteValidation.getError());

        return  new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }



    @PostMapping(value=api.Internal.Person.ADD)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PersonView> add(@Valid @RequestBody PersonRequest personRequest)
    {
        Person person= this.service.add(personRequest);
        return new ResponseEntity<>(converter.apply(person),HttpStatus.CREATED);
    }




    @PutMapping(value = api.Internal.Person.UPDATE)
    public ResponseEntity update(@Valid @RequestBody PersonRequest personRequest, @PathVariable("id") Long id ) throws Exception {

        ValidationResult<Person> updateValidation = this.service.update(personRequest, id);
        if(!updateValidation.isValid())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(updateValidation.getError());

        return new ResponseEntity<>(converter.apply(updateValidation.get()), HttpStatus.CREATED);


    }



    @GetMapping()
    public ResponseEntity findAll() throws Exception {
        ValidationResult<List<Person>>result= service.findAll();
        if(!result.isValid())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result.getError());

        List<PersonView> listPersonView = new ArrayList<>();
        for (Person person : result.get()) {
            listPersonView.add(converter.apply(person));
        }

        return ResponseEntity.ok(listPersonView);

    }




}
