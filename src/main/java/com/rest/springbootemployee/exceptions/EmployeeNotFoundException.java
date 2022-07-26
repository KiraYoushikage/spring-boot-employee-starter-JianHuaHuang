package com.rest.springbootemployee.exceptions;

import lombok.Data;

@Data
public class EmployeeNotFoundException extends RuntimeException{
    String code="400";
    public EmployeeNotFoundException() {
        super("EmployeeNotFoud");
    }
}
