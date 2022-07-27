package com.rest.springbootemployee.dao;

import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.exceptions.EmployeeNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class EmployeeRepository {


    private List<Employee> employeeList;


    {
        System.out.println("来这里了没");
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


    public List<Employee> getAll() {
        return this.employeeList;
    }

    public void updateEmployeeList(Integer id,Employee employee) {
        for (int i = 0; i < employeeList.size(); i++) {
            if (Objects.equals(employeeList.get(i).getId(), id)){
                employeeList.set(i,employee);
                break;
            }
        }
    }

    public void clearAll() {

        this.employeeList = new ArrayList<>();
    }

    public Employee insertEmployee(Employee employee) {
        this.employeeList.add(employee);
        return employee;
    }

    public Employee findById(Integer id) {
        return this.employeeList.stream()
                .filter(employee -> Objects.equals(employee.getId(), id))
                .findFirst()
                .orElseThrow(EmployeeNotFoundException::new);

    }

    public Employee deleteEmployee(Integer id){
        Employee employee=null;
        for (int i = 0; i < employeeList.size(); i++) {
            if (Objects.equals(employeeList.get(i).getId(), id)){
                employee=employeeList.get(i);
                employeeList.remove(i);
                break;
            }
        }
        return employee;
    }
}
