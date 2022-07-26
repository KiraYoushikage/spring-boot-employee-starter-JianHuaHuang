package com.rest.springbootemployee.dao;

import com.rest.springbootemployee.Employee;
import com.rest.springbootemployee.exceptions.EmployeeNotFoundException;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepository {

    private final List<Employee> employeeList;


    {
        employeeList = new ArrayList<Employee>() {{
            add(new Employee(1, "A", 10, "男", 100));
            add(new Employee(2, "B", 11, "女", 100));
            add(new Employee(3, "C", 12, "男", 100));
            add(new Employee(4, "D", 13, "女", 100));
            add(new Employee(5, "E", 14, "男", 100));
            add(new Employee(6, "F", 10, "女", 100));
            add(new Employee(7, "G", 11, "男", 100));
            add(new Employee(8, "H", 12, "女", 100));
            add(new Employee(9, "I", 13, "男", 100));
            add(new Employee(10, "J", 14, "女", 100));
        }};
    }

    public List<Employee> findAll() {
        return employeeList;
    }

    public Employee findById(int id) {
        return employeeList.stream()
                .filter(employee -> employee.getId() == id
                ).findFirst()
                .orElseThrow(EmployeeNotFoundException::new);
    }

    public List<Employee> findByGender(String gender) {
        return employeeList.stream()
                .filter(employee -> employee.getGender().equals(gender))
                .collect(Collectors.toList());
    }
}
