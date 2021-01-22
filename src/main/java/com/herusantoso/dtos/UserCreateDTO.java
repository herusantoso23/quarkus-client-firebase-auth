package com.herusantoso.dtos;

import lombok.Data;

import javax.json.bind.annotation.JsonbProperty;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UserCreateDTO {

    @Email(message = "Please enter your email in format yourname@example.com")
    @NotBlank(message = "Email must not be empty")
    private String email;

    @JsonbProperty(value = "display_name")
    @NotBlank(message = "Display name must not be empty")
    @Size(min = 3, max = 50, message = "Length of display name min {min} and max {max} character")
    private String displayName;

    @NotBlank(message = "Password must not be empty")
    @Size(min = 6, max = 50, message = "Length of password min {min} and max {max} character")
    private String password;

    @JsonbProperty(value = "photo_url")
    private String photoUrl;

    @JsonbProperty(value = "phone_number")
    private String phoneNumber;

}
