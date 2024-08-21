package com.example.test.Model.view;

import com.example.test.Model.Entity.Person;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonView {

    private long id;
    private String nom;
    private String prenom;
    private String adress;
    private String Email;
    public static PersonView toView(Person person){
        return PersonView.builder()
                .id(person.getId())
                .nom(person.getNom()).
                prenom(person.getPrenom())
                .adress(person.getAdress())
                .Email(person.getEmail())
                .build();
    }

}
