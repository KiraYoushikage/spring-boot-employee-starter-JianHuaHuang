package com.rest.springbootemployee.controller;

import com.rest.springbootemployee.service.EmployeeService;
import com.rest.springbootemployee.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {


    @Autowired
    EmployeeService companyService;
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Employee findById(@PathVariable(value = "id") int id){
        return companyService.findById(id);
    }
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> findAll(){
        return companyService.findAll();
    }
    @GetMapping(params = {"gender"})
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> findByGender(@RequestParam(value = "gender") String gender) {
        return companyService.findByGender(gender);
    }
    //TODO 类型
    @GetMapping(params = {"page","pageSize"})
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> findByPage(int page ,int pageSize){
        return companyService.findByPage(page,pageSize);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Employee insertEmployee(@RequestBody Employee employee){
        return companyService.insertEmployee(employee);
    }

    @PutMapping("/{id}")
    public  Employee updateEmployee(@PathVariable Integer id,@RequestBody Employee employee){
        // TODO 不加@RequestBody
        return companyService.updateEmployee(id,employee);
    }

    // TODO 定位，表示资源的路径
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public  Employee deleteEmployee(@PathVariable int id){
        return companyService.deleteEmployee(id);
    }

}
