package com.rest.springbootemployee.controller;

import com.rest.springbootemployee.Employee;
import com.rest.springbootemployee.dao.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {


    @Autowired
    EmployeeRepository employeeRepository;
//    @GetMapping("/{id}")
//    public Employee findById(@PathVariable(value = "id") int id){
//        return employeeRepository.findById(id);
//    }
    @GetMapping("all")
    public List<Employee> findAll(){
        return employeeRepository.findAll();
    }
}
