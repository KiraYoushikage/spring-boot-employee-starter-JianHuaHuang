package com.rest.springbootemployee.controller;

import com.rest.springbootemployee.Employee;
import com.rest.springbootemployee.dao.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {


    @Autowired
    EmployeeRepository employeeRepository;
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Employee findById(@PathVariable(value = "id") int id){
        return employeeRepository.findById(id);
    }
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> findAll(){
        return employeeRepository.findAll();
    }
    @GetMapping(params = {"gender"})
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> findByGender(@RequestParam(value = "gender") String gender) {
        return employeeRepository.findByGender(gender);
    }

}
