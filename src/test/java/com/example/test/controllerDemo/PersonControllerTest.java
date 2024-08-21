package com.example.test.controllerDemo;


import com.example.test.Model.Entity.Person;

import com.example.test.Model.converters.PersonToPersonViewConverter;
import com.example.test.Model.request.PersonRequest;
import com.example.test.Model.view.PersonView;

import com.example.test.serviceDemo.ErrorResponse;
import com.example.test.serviceDemo.PersonService;
import com.example.test.serviceDemo.ValidationResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PersonController.class)
@ExtendWith(MockitoExtension.class)
class PersonControllerTest {

    @MockBean
    private PersonService testService;
    @MockBean
    private PersonToPersonViewConverter personToPersonViewConverter;

    @InjectMocks
    private PersonController testController;




    private MockMvc mockMvc;

    private Person person1;
    private PersonView personView1;
    private PersonView personView2;

    long id ;
    String validPersonJson;
    PersonRequest personRequest;
    String invalidPersonJson;
    Person person2;
    @BeforeEach
    public void setUp()
    {

        mockMvc = MockMvcBuilders.standaloneSetup(testController).build();
        person1 = Person.builder()
                .id(1L)
                .nom("test")
                .prenom("bentest")
                .adress("tunis")
                .email("testbentest@test.test")
                .build();
         person2 = Person.builder()
                 .id(2L)
                .nom("person")
                .prenom("benperson")
                .adress("tunis")
                .email("personbenperson@person.person")
                .build();
        personView1 = PersonView.builder()
                .id(1L)
                .nom("test")
                .prenom("bentest")
                .adress("tunis")
                .Email("testbentest@test.test")
                .build();
        personView2 = PersonView.builder()
                .id(2L)
                .nom("person")
                .prenom("benperson")
                .adress("tunis")
                .Email("personbenperson@person.person")
                .build();
        personRequest = PersonRequest.builder()
                .id(1L)
                .nom("test")
                .prenom("bentest")
                .adress("tunis")
                .Email("testbentest@test.test")
                .build();
        id=2L;
        validPersonJson = "{\"nom\":\"test\", \"prenom\":\"bentest\", \"adress\":\"tunis\", \"email\":\"testbentest@gmail.test\"}";
        invalidPersonJson = "{\"nom\":\"tes2t\", \"prenom\":\"bentest\", \"adress\":\"tunis\", \"email\":\"testbentest@gmail.test\"}";
    }



    @Nested
    public class TestFindById {
        @Test
        public void longId_FindById_PersonFound_ReturnResponseEntityPersonView() throws Exception {
            //ARRANGE
            when(testService.findById(Mockito.any(Long.class))).thenReturn(ValidationResult.valid(person1));
            when(personToPersonViewConverter.apply(person1)).thenReturn(personView1);
            //ACT
           // ResponseEntity<PersonView> personFound = testController.findById(id);

            //ASSERT
            mockMvc.perform(get("/api/internal/persons/find/2"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.id").value(person1.getId()))
                    .andExpect(jsonPath("$.nom").value(person1.getNom()))
                    .andExpect(jsonPath("$.prenom").value(person1.getPrenom()))
                    .andExpect(jsonPath("$.adress").value(person1.getAdress()))
                    .andExpect(jsonPath("$.email").value(person1.getEmail()));
        }


        @Test
        public void longId_FindById_PersonNotFound_ThrowResponeStatusExceptionNotFound() throws Exception {
            //ARRANGE
            when(testService.findById(Mockito.any(Long.class)))
                    .thenReturn(ValidationResult.invalid(ErrorResponse.valueOf("Person not found",String.format("The requested ID %d does not exist in the database.",id))));

            //ACT ASSERT

            mockMvc.perform(get("/api/internal/persons/find/"+id))
                    .andExpect(status().isNotFound())
                   .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.errorCode")
                            .value("Person not found"))
                    .andExpect(jsonPath("$.errorMessage")
                            .value(String.format("The requested ID %d does not exist in the database.",id)));
        }

    }


    @Nested
    public class DeleteTest
    {

        @Test
        public void validLongId_Delete_ReturnVoid() throws Exception {
            when(testService.delete(Mockito.any(Long.class))).thenReturn(ValidationResult.valid(id));

            mockMvc.perform(delete("/api/internal/persons/delete/"+id))
                    .andExpect(status().isNoContent());


        }

        @Test
        public void longIdUnValidId_Delete_ThrowNotFoundException() throws Exception {

            when(testService.delete(Mockito.any(Long.class))).thenReturn(ValidationResult.invalid(ErrorResponse.valueOf("Person not found",String.format("The requested ID %d does not exist in the database.",id))));

            mockMvc.perform(delete("/api/internal/persons/delete/"+id))
                    .andExpect(status().isNotFound())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.errorCode")
                            .value("Person not found"))
                    .andExpect(jsonPath("$.errorMessage")
                            .value(String.format("The requested ID %d does not exist in the database.",id)));

        }
    }

    @Nested
    public class AddTest
    {
        @Test
        public void valid_AddPerson_ReturnPersonView() throws Exception {
            //ARRANGE
            when(testService.add(Mockito.any(PersonRequest.class))).thenReturn(person1);


            //ACT ASSERTION




            mockMvc.perform(post("/api/internal/persons/add")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(validPersonJson))
                    .andExpect(status().isCreated())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.id").value(person1.getId()))
                    .andExpect(jsonPath("$.nom").value(person1.getNom()))
                    .andExpect(jsonPath("$.prenom").value(person1.getPrenom()))
                    .andExpect(jsonPath("$.adress").value(person1.getAdress()))
                    .andExpect(jsonPath("$.email").value(person1.getEmail()));






        }

        @Test
        public void invalidBody_Add_ThrowsMethodArgumentNotValidException() throws Exception {



            mockMvc.perform(post("/api/internal/persons/add")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(invalidPersonJson))
                    .andExpect(status().isBadRequest())
                    .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));

        }





    }

    @Nested
    public class UpdateTest
    {
        @Test
        public void personRequestandLongId_Update_ReturnPeronView() throws Exception {
            //ARRANGE

            when(testService.update(Mockito.any(PersonRequest.class),Mockito.any(Long.class))).thenReturn(ValidationResult.valid(person1));

            //ACT ASSERT

            mockMvc.perform(put("/api/internal/persons/update"+id)
                    .contentType(MediaType.APPLICATION_JSON)
                            .content(validPersonJson))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").value(person1.getId()))
                    .andExpect(jsonPath("$.nom").value(person1.getNom()))
                    .andExpect(jsonPath("$.prenom").value(person1.getPrenom()))
                    .andExpect(jsonPath("$.adress").value(person1.getAdress()))
                    .andExpect(jsonPath("$.email").value(person1.getEmail()));

        }


        @Test
        public void personRequestLongNotPresentId_Update_ThrowsResponseStatusNotFound() throws Exception {
            //ARRANGE

           when(testService.update(Mockito.any(PersonRequest.class),Mockito.any(Long.class))).thenReturn(ValidationResult.invalid( ErrorResponse.valueOf("Person not found",String.format("The requested ID %d does not exist in the database.",id))));


            //ACT

            mockMvc.perform(put("/api/internal/persons/update/"+id)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(validPersonJson))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.errorCode")
                            .value("Person not found"))
                    .andExpect(jsonPath("$.errorMessage")
                            .value(String.format("The requested ID %d does not exist in the database.",id)));







        }


        @Test
        public void wrongTypeId_Update_ThrowsException() throws Exception {


            mockMvc.perform(put("/api/internal/persons/update/p")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(validPersonJson))
                    .andExpect(status().isBadRequest())
                    .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof MethodArgumentTypeMismatchException));

        }
        @Test
        public void invalidBody_Update_ThrowsException() throws Exception {

            mockMvc.perform(put("/api/internal/persons/update/2")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(invalidPersonJson))
                    .andExpect(status().isBadRequest())
                    .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));

        }


    }

    @Nested
    public class FindAllTest
    {
        @Test
        public void findAll_ReturnListPersonView() throws Exception {
            //ARRANGE
            List<Person>personsFoundByService=new ArrayList<>();


            when(testService.findAll()).thenReturn(ValidationResult.valid(List.of(person1,person2)));
            //ACT ASSERT
            mockMvc.perform(get("/api/internal/persons"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$",hasSize(2)));






        }

        @Test
        public void findAll_ThrowsResponseStatusExceptionNotFound() throws Exception {

           //ARRANGE
            when(testService.findAll()).thenReturn(ValidationResult.invalid( ErrorResponse.valueOf("Not Found","Empty dataBase")));

            //ACT & ASSERT


            mockMvc.perform(get("/api/internal/persons")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.errorCode")
                            .value("Not Found"))
                    .andExpect(jsonPath("$.errorMessage")
                            .value("Empty dataBase"));


        }
    }
}
