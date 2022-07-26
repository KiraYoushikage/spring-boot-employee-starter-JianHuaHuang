package com.rest.springbootemployee.dao;

import com.rest.springbootemployee.Employee;
import com.rest.springbootemployee.exceptions.EmployeeNotFoundException;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepository {

    private List<Employee> employeeList;


    public EmployeeRepository() {
        employeeList=new ArrayList<>();
        employeeList.add(new Employee(1,"小明",10,"男",100));
        employeeList.add(new Employee(2,"小明1",11,"男",100));
        employeeList.add(new Employee(3,"小明2",12,"男",100));
        employeeList.add(new Employee(4,"小明3",13,"男",100));
        employeeList.add(new Employee(5,"小明4",14,"男",100));
    }

    public List<Employee> findAll(){
        return employeeList;
    }
    public Employee findById(int id){
        return employeeList.stream()
                .filter(employee -> employee.getId()==id
                ).findFirst()
                .orElseThrow(EmployeeNotFoundException::new);
    }

    public List<Employee> findByGender(String gender) {
        return employeeList.stream()
                .filter(employee -> employee.getGender().equals(gender))
                .collect(Collectors.toList());
    }
}
