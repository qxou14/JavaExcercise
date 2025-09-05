package com.example.store.dtos;

import com.example.store.validation.Lowercase;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;


//this dto is sharing the user entity and include password field

@Data //include getter, setter, tostring, tohashcode
public class RegisterUserRequest {
    @NotBlank(message = "name is required")
    @Size(max = 255, message ="name must be less than 255 characters")
    private String name;
    @NotBlank(message = "email is required")
    @Email(message = "email must be valid")
    @Lowercase(message ="email cannot be uppercase")  //this is custom validation created by me. take a look at validation pacakge
    private String email;
    @NotBlank(message = "password is required")
    @Size(min = 6, message = "password must be more than 6 character")
    private String password;
}
