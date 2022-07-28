package com.rest.springbootemployee.service;


import com.rest.springbootemployee.dao.CompanyRepository;
import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.exceptions.CompanyNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
public class CompanyService {

    @Autowired
    CompanyRepository companyRepository;

    public List<Company> findAll(){
        return companyRepository.getAll();
    }

    public Company findById( int id ){
        List<Company> companyList=companyRepository.getAll();
        return companyList.stream()
                .filter(company -> company.getId()==id)
                .findFirst()
                .orElseThrow(CompanyNotFoundException::new);
    }

    public List<Employee> findAllEmployeesById(int id) {
        return findById(id).getEmployees();
    }

    public List<Company> findByPage(int page, int pageSize) {
        List<Company> companyList=companyRepository.getAll();
        return companyList.stream()
                .skip((long) (page - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public Company insertCompany(Company company) {
        List<Company> companyList=companyRepository.getAll();
        int maxId=companyList.stream().mapToInt(Company::getId).max().orElse(-1);
        company.setId(maxId+1);
        companyList.add(company);
        companyRepository.updateCompanyList(companyList);
        return company;
    }

    public Company updateCompany(Integer id,Company company) {
        List<Company> companyList=companyRepository.getAll();
        Company res=null;
        if(Objects.isNull(id))return null;
        for (int i = 0; i < companyList.size(); i++) {
            if (Objects.equals(id, companyList.get(i).getId())){
                res=companyList.get(i);
                companyList.set(i,company);
                break;
            }
        }
        companyRepository.updateCompanyList(companyList);
        return res;
    }

    public Company deleteCompany(Integer id) {
        List<Company> companyList=companyRepository.getAll();
        Company company = null;
        for (int i = 0; i < companyList.size(); i++) {
            if (Objects.equals(id, companyList.get(i).getId())){
                company=companyList.get(i);
                companyList.remove(i);
                break;
            }
        }
        companyRepository.updateCompanyList(companyList);
        return company;
    }
}
