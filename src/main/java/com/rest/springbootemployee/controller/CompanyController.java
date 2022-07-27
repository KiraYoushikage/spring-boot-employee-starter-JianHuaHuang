package com.rest.springbootemployee.controller;


import com.rest.springbootemployee.service.CompanyService;
import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    CompanyService companyService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Company> findAll(){
        return companyService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Company findById(@PathVariable(value = "id") int id){
        return companyService.findById(id);
    }
    @GetMapping("/{id}/employees")
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> findAllEmployeesById(@PathVariable(value = "id") int id){
        return companyService.findAllEmployeesById(id);
    }


    @GetMapping(params = {"page","pageSize"})
    @ResponseStatus(HttpStatus.OK)
    public List<Company> findByPage(int page ,int pageSize){
        return companyService.findByPage(page,pageSize);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Company insertCompany(Company company){
        return companyService.insertCompany(company);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.ACCEPTED)
    public  Company updateCompany(Company company){
        return companyService.updateCompany(company);
    }
    @DeleteMapping(params = {"id"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public  Company deleteCompany(@RequestParam int id){
        return companyService.deleteCompany(id);
    }
}
