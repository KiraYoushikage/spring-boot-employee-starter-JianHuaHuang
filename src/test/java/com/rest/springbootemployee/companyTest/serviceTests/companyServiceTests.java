//package com.rest.springbootemployee.companyTest.serviceTests;
//
//import com.rest.springbootemployee.dao.CompanyJpaRepository;
//import com.rest.springbootemployee.dao.impl.CompanyRepository;
//import com.rest.springbootemployee.entity.Company;
//import com.rest.springbootemployee.entity.Employee;
//import com.rest.springbootemployee.service.CompanyService;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.mockito.BDDMockito.given;
//
//@ExtendWith(SpringExtension.class)
//public class companyServiceTests {
//
//    @Mock
//    CompanyRepository companyRepository;
//
//    @InjectMocks
//    CompanyService companyService;
//
//    @Mock
//    CompanyJpaRepository companyJpaRepository;
//    List<Company> prepareCompanyList=new ArrayList<>();
//
//    @BeforeEach
//    public void setUp() {
//
//        for (int i = 1, j=1 ; i <=10 ; i++) {
//            Company company=new Company(i,"company"+i,new ArrayList<>());
//            for(int k=0;k<5;k++,j++){
//                company.getEmployees().add(new Employee(j, "employee"+j,(int)(Math.random()*35) + 20, (j&1)==0 ? "女": "男", (int)(Math.random()*15000) + 10000,i));
//            }
//            prepareCompanyList.add(company);
//        }
//        Mockito.when(companyJpaRepository.findAll()).thenReturn(prepareCompanyList);
//        System.out.println("----TestStart----");
//    }
//    @AfterEach
//    void after() {
//        System.out.println("----TestStop----");
//    }
//
//
//    @Test
//    public void should_return_all_employee_when_findAll_given_nothing() {
//        //given
//        List<Company> preparedCompanys=new ArrayList<>();
//        for (int i = 1; i <= 2; i++) {
//            Employee employee = new Employee(i, "Sally" + i, 22, "男", 10000,1);
//            preparedCompanys.add(new Company(1,"company"+i,employee));
//        }
//
//        //when
//        Mockito.when(companyJpaRepository.findAll()).thenReturn(preparedCompanys);
//        List<Company> companyList = companyService.findAll();
//        //then
//        Assertions.assertEquals(2,companyList.size());
//        Assertions.assertEquals(preparedCompanys, companyList);
//    }
//
//    @Test
//    public void should_update_only_age_and_salary_when_update_given_employee() {
//        //given
//
//        Employee prepareEmployee = new Employee(2, "Sally" + 2, 20, "女", 12000,1);
//        //when
//        Mockito.when(employeeJpaRepository.findById(2)).thenReturn(Optional.of(prepareEmployee));
//        Employee employee = employeeService.updateEmployee(2, prepareEmployee);
//        //then
//        Assertions.assertEquals(prepareEmployee.getId(), employee.getId());
//        Assertions.assertEquals(prepareEmployee.getName(), employee.getName());
//        Assertions.assertEquals(prepareEmployee.getAge(), employee.getAge());
//        Assertions.assertEquals(prepareEmployee.getGender(), employee.getGender());
//        Assertions.assertEquals(prepareEmployee.getSalary(), employee.getSalary());
//
//
//    }
//
//    @Test
//    public void should_return_insert_employee_when_insertEmployee_given_employee() {
//        //given
//        Employee prepareEmployee = new Employee(2, "Sally" + 2, 20, "女", 12000,1);
//        Mockito.when(employeeJpaRepository.save(prepareEmployee)).thenReturn(prepareEmployee);
//
//        //when
//        Employee employee = employeeService.insertEmployee(prepareEmployee);
//        //then
//        Assertions.assertEquals(prepareEmployee.getId(), employee.getId());
//        Assertions.assertEquals(prepareEmployee.getName(), employee.getName());
//        Assertions.assertEquals(prepareEmployee.getAge(), employee.getAge());
//        Assertions.assertEquals(prepareEmployee.getGender(), employee.getGender());
//        Assertions.assertEquals(prepareEmployee.getSalary(), employee.getSalary());
//
//    }
//
//
//    @Test
//    public void should_return_delete_employee_when_deleteEmployee_given_id() {
//        //given
//        Integer id = 2;
//        //when
//        Employee returnEmployee = new Employee(2, "Sally" + 2, 22, "男", 10000,1);
//        given(employeeJpaRepository);
//        employeeService.deleteEmployee(2);
//        //then
//    }
//}
