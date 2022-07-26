package com.rest.springbootemployee.controller;


import com.rest.springbootemployee.dao.CompanyRepository;
import com.rest.springbootemployee.entity.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    CompanyRepository companyRepository;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Company> findAll(){
        return companyRepository.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Company findById(@PathVariable(value = "id") int id){
        System.out.println("来了没");
        return companyRepository.findById(id);
    }
}
