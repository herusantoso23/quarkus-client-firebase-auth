package com.herusantoso.dtos;

import lombok.Data;

import java.util.Collection;

@Data
public class ResultPageDTO {

    private Collection data;
    private int size;
    private int page;
    private long pages;

}
