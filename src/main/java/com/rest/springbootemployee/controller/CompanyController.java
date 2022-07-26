package com.rest.springbootemployee.controller;


import com.rest.springbootemployee.dao.CompanyRepository;
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
    CompanyRepository companyRepository;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Company> findAll(){
        return companyRepository.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Company findById(@PathVariable(value = "id") int id){
        return companyRepository.findById(id);
    }
    @GetMapping("/{id}/employees")
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> findAllEmployeesById(@PathVariable(value = "id") int id){
        return companyRepository.findAllEmployeesById(id);
    }


    @GetMapping(params = {"page","pageSize"})
    @ResponseStatus(HttpStatus.OK)
    public List<Company> findByPage(int page ,int pageSize){
        return companyRepository.findByPage(page,pageSize);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Company insertEmployee(Company company){
        return companyRepository.insertCompany(company);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.ACCEPTED)
    public  Company updateEmployee(Company company){
        return companyRepository.updateEmployee(company);
    }
//
//    // todo 为啥这里使用put会报错
//    @DeleteMapping(params = {"id"})
//    @ResponseStatus(HttpStatus.ACCEPTED)
//    public  Employee deleteEmployee(@RequestParam int id){
//        return employeeRepository.deleteEmployee(id);
//    }
}
