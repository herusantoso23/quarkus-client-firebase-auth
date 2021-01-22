package com.herusantoso.dtos;

import lombok.Data;

import javax.json.bind.annotation.JsonbProperty;

@Data
public class ResultErrorDTO {

    @JsonbProperty(value = "error_code")
    private String errorCode;
    private String message;
    private Object result;

}
