package com.rest.springbootemployee.controller;

import com.rest.springbootemployee.service.EmployeeService;
import com.rest.springbootemployee.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {


    @Autowired
    EmployeeService employeeService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Employee findById(@PathVariable(value = "id") int id) {
        return employeeService.findById(id);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> findAll() {
        return employeeService.findAll();
    }

    @GetMapping(params = {"gender"})
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> findByGender(@RequestParam(value = "gender") String gender) {
        return employeeService.findByGender(gender);
    }

    //TODO 类型
    @GetMapping(params = {"page", "pageSize"})
    @ResponseStatus(HttpStatus.OK)
    public Page<Employee> findByPage(int page, int pageSize) {
        return employeeService.findByPage(page, pageSize);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Employee insertEmployee(@RequestBody Employee employee) {
        return employeeService.insertEmployee(employee);
    }

    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable Integer id, @RequestBody Employee employee) {
        // TODO 不加@RequestBody
        return employeeService.updateEmployee(id, employee);
    }

    // TODO 定位，表示资源的路径
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable int id) {
        employeeService.deleteEmployee(id);
    }

}
