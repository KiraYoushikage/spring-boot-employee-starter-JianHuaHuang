package com.rest.springbootemployee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {



    private Integer id;

    private String name;

    private Integer age;

    private String gender;

    private Integer salary;

    public Employee(Integer id) {
        this.id = id;
    }
}
