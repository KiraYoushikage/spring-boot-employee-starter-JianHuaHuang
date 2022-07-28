package com.rest.springbootemployee.controller;


import com.rest.springbootemployee.service.CompanyService;
import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    CompanyService companyService;

    @GetMapping
    public List<Company> findAll(){
        return companyService.findAll();
    }

    @GetMapping("/{id}")
    public Company findById(@PathVariable(value = "id") int id){
        return companyService.findById(id);
    }
    @GetMapping("/{id}/employees")
    public List<Employee> findAllEmployeesById(@PathVariable(value = "id") int id){
        return companyService.findAllEmployeesById(id);
    }


    @GetMapping(params = {"page","pageSize"})
    public Page<Company> findByPage(int page , int pageSize){
        return companyService.findByPage(page,pageSize);
    }

    @PostMapping()
    public Company insertCompany(@RequestBody Company company){
        return companyService.insertCompany(company);
    }

    @PutMapping("/{id}")
    public  Company updateCompany(@PathVariable Integer id, @RequestBody Company company){
        return companyService.updateCompany(id,company);
    }
    @DeleteMapping({"/{id}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public  void deleteCompany(@PathVariable Integer id){
        companyService.deleteCompany(id);
    }
}
