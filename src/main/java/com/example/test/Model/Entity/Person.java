package com.example.test.Model.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Email;




@Entity
@AllArgsConstructor
@NoArgsConstructor

@Table(name = "PERSON" )

@Data
@Builder
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;
    @NotNull(message = "Name can't be null")
    @Pattern(regexp = "[A-Za-z]*", message ="Invalid name")
    @Column(name = "NOM")
    private String nom;

    @NotNull(message = "Lastname can't be null")
    @Pattern(regexp = "[A-Za-z]*", message ="Invalid lastname")
    @Column(name = "PRENOM")
    private String prenom;

    @Column(name = "ADRESS")
    @NotNull(message = "Adress can't be null")
    private String adress;

   // @Column(name = "EMAIL")
    @Email(message ="Invalid Email" )
    private String email;






}
