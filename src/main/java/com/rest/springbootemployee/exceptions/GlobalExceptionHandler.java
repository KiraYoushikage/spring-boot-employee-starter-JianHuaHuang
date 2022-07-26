package com.rest.springbootemployee.exceptions;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ResponseBody
    @ExceptionHandler(value = EmployeeNotFoundException.class)
    public Map<String, String> myErrorHandler(EmployeeNotFoundException ex) {
        Map<String, String> resMap = new HashMap<>();
        resMap.put("code", ex.getCode());
        resMap.put("msg", ex.getMessage());
        return resMap;
    }

}
