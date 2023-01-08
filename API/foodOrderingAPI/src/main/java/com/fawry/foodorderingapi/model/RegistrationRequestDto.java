package com.fawry.foodorderingapi.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Data
public class RegistrationRequestDto {

    private Long id;
    @Size(min = 2, max = 400, message = "The User Name is not valid")

    @Size(min = 2, max = 400, message = "The User Name is not valid")
    private String name;

    @NotNull(message = "Phone is null")
    private String phone;

    @Size(min = 2, max = 400, message = "The User email is not valid")
    private String email;

    @Size(min = 6, max = 4000, message = "The Password is not valid")
    private String password;

}
