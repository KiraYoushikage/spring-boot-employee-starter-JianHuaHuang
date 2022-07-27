package com.rest.springbootemployee.service;

import com.rest.springbootemployee.dao.EmployeeRepository;
import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.exceptions.EmployeeNotFoundException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    public List<Employee> findAll() {
        return employeeRepository.getAll();
    }

    public Employee findById(int id) {
        List<Employee> employeeList=employeeRepository.getAll();
        return employeeList.stream()
                .filter(employee -> employee.getId() == id
                ).findFirst()
                .orElseThrow(EmployeeNotFoundException::new);
    }

    public List<Employee> findByGender(String gender) {
        List<Employee> employeeList=employeeRepository.getAll();
        return employeeList.stream()
                .filter(employee -> employee.getGender().equals(gender))
                .collect(Collectors.toList());
    }

    public List<Employee> findByPage(int page, int pageSize) {
        List<Employee> employeeList=employeeRepository.getAll();
        return employeeList.stream()
                .skip((long) (page - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public Employee insertEmployee(Employee employee) {
        List<Employee> employeeList=employeeRepository.getAll();
        int maxId=employeeList.stream().mapToInt(Employee::getId).max().orElse(-1);
        employee.setId(maxId+1);
        employeeList.add(employee);
        return employeeRepository.insertEmployee(employee);
    }

    public Employee updateEmployee(Integer id, Employee employee) {

        Employee em=employeeRepository.findById(employee.getId());
        if(StringUtils.isNoneBlank(employee.getName())){
            em.setName(employee.getName());
        }
        if (StringUtils.isNoneBlank(employee.getGender())){
            em.setGender(employee.getGender());
        }
        if(Objects.nonNull(employee.getAge())){
            em.setAge(employee.getAge());
        }
        if (Objects.nonNull(employee.getSalary())){
            em.setSalary(employee.getSalary());
        }
        employeeRepository.updateEmployeeList(id,em);
        return em;
    }

    public Employee deleteEmployee(int id) {
            return employeeRepository.deleteEmployee(id);
    }
}
