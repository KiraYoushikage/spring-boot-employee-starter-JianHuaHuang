package com.rest.springbootemployee.service;

import com.rest.springbootemployee.dao.EmployeeRepository;
import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.exceptions.EmployeeNotFoundException;

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
        employeeRepository.updateEmployeeList(employeeList);
        return employee;
    }

    public Employee updateEmployee(Employee employee) {
        List<Employee> employeeList=employeeRepository.getAll();
        Employee res=null;
        for (int i = 0; i < employeeList.size(); i++) {
            if (Objects.equals(employee.getId(), employeeList.get(i).getId())){
                res=employeeList.get(i);
                employeeList.set(i,employee);
                break;
            }
        }
        employeeRepository.updateEmployeeList(employeeList);
        return res;
    }

    public Employee deleteEmployee(int id) {
        List<Employee> employeeList=employeeRepository.getAll();
        Employee employee = null;
        // TODO 边界值可以自己throw问题，
        for (int i = 0; i < employeeList.size(); i++) {
            if (Objects.equals(id, employeeList.get(i).getId())){
                employee=employeeList.get(i);
                employeeList.remove(i);
                break;
            }
        }
        employeeRepository.updateEmployeeList(employeeList);
        return employee;
    }
}
