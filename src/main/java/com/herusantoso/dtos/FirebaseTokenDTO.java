package com.herusantoso.dtos;

import lombok.Data;

import javax.json.bind.annotation.JsonbProperty;
import javax.validation.constraints.NotBlank;

@Data
public class FirebaseTokenDTO {

    @JsonbProperty(value = "token_id")
    @NotBlank(message = "Token id must not be empty")
    private String tokenId;

}
