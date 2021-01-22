package com.herusantoso.dtos;

import lombok.Data;

@Data
public class ResultObjectValidationDTO {

    private String object;
    private String message;
    private Object value;

}
