package com.herusantoso.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RoleDTO {

    private String id;

    @NotBlank
    private String name;

}
