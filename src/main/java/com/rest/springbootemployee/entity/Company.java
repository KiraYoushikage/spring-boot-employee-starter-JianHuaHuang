package com.rest.springbootemployee.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Company {
    private int id;
    private String companyName;
    private List<Employee> employees;

    public Company(int id, String companyName, Employee employee) {
        this.id = id;
        this.companyName = companyName;
        if (this.getEmployees()!=null){
            this.getEmployees().add(employee);
        }
    }
}
