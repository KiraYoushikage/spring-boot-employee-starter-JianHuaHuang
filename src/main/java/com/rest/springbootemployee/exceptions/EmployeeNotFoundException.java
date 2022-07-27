package com.rest.springbootemployee.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class EmployeeNotFoundException extends RuntimeException{
    String code="400";
    public EmployeeNotFoundException() {
        super("EmployeeNotFoud");
    }
}
