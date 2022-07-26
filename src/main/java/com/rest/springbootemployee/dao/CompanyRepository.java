package com.rest.springbootemployee.dao;


import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.exceptions.CompanyNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

    public Company insertCompany(Company company) {
        int maxId=companyList.stream().mapToInt(Company::getId).max().orElse(-1);
        company.setId(maxId+1);
        companyList.add(company);
        return company;
    }

    public Company updateEmployee(Company company) {
        Company res=null;
        for (int i = 0; i < companyList.size(); i++) {
            if (Objects.equals(company.getId(), companyList.get(i).getId())){
                res=companyList.get(i);
                companyList.set(i,company);
                break;
            }
        }
        return res;
    }
}
