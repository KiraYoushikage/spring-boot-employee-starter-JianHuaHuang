package com.rest.springbootemployee.companyTest.serviceTests;

import com.rest.springbootemployee.dao.CompanyRepository;
import com.rest.springbootemployee.dao.EmployeeRepository;
import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.service.CompanyService;
import com.rest.springbootemployee.service.EmployeeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
public class companyServiceTests {

    @Mock
    CompanyRepository companyRepository;
    @InjectMocks
    CompanyService companyService;

    List<Company> prepareCompanyList=new ArrayList<>();

    @BeforeEach
    public void setUp() {

        for (int i = 1, j=1 ; i <=10 ; i++) {
            Company company=new Company(i,"company"+i,new ArrayList<>());
            for(int k=0;k<5;k++,j++){
                company.getEmployees().add(new Employee(j, "employee"+j,(int)(Math.random()*35) + 20, (j&1)==0 ? "女": "男", (int)(Math.random()*15000) + 10000));
            }
            prepareCompanyList.add(company);
        }
        Mockito.when(companyRepository.getAll()).thenReturn(prepareCompanyList);
        System.out.println("----TestStart----");
    }





    @AfterEach
    void after() {
        System.out.println("----TestStop----");
    }
}
