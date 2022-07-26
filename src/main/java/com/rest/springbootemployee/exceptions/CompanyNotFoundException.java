package com.rest.springbootemployee.exceptions;

import lombok.Data;

@Data
public class CompanyNotFoundException extends RuntimeException{
    private String code ="400";
    public CompanyNotFoundException() {
        super("CompanyNotFoundException");
    }
}
