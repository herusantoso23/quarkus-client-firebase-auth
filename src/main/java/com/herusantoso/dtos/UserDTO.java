package com.herusantoso.dtos;

import lombok.Data;

import javax.json.bind.annotation.JsonbProperty;

@Data
public class UserDTO {

    private String id;

    private String email;

    @JsonbProperty(value = "display_name")
    private String displayName;

}
