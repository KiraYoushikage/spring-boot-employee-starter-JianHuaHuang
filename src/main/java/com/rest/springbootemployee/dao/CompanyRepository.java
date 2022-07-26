package com.rest.springbootemployee.dao;


import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.exceptions.CompanyNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Repository
public class CompanyRepository {

    List<Company>companyList;

    {
        companyList=new ArrayList<>();

        for (int i = 1, j=1 ; i <=10 ; i++) {
            Company company=new Company(i,"company"+i,new ArrayList<>());
            for(int k=0;k<5;k++,j++){
                company.getEmployees().add(new Employee(j, "employee"+j,(int)(Math.random()*35) + 20, (j&1)==0 ? "女": "男", (int)(Math.random()*15000) + 10000));
            }
            companyList.add(company);
        }

    }

    public List<Company> findAll(){
        return companyList;
    }

    public Company findById( int id ){
        return companyList.stream()
                .filter(company -> company.getId()==id)
                .findFirst()
                .orElseThrow(CompanyNotFoundException::new);
    }

    public List<Employee> findAllEmployeesById(int id) {
        return findById(id).getEmployees();
    }

    public List<Company> findByPage(int page, int pageSize) {
        return companyList.stream()
                .skip((long) (page - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }
}
