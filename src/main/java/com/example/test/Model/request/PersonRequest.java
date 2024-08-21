package com.example.test.Model.request;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor


public class PersonRequest {
    private long id;

    @NotNull(message = "Name can't be null")
    @Pattern(regexp = "[A-Za-z]*", message ="Invalid name")
    private String nom;

    @NotNull(message = "Lastname can't be null")
    @Pattern(regexp = "[A-Za-z]*", message ="Invalid lastname")
    private String prenom;

    @NotNull(message = "Adress can't be null")
    private String adress;

  //  @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$",message ="Invalid Email")
    @Email(message ="Invalid Email" )
    private String Email;
}
