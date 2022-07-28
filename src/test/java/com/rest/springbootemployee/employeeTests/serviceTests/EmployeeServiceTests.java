package com.rest.springbootemployee.employeeTests.serviceTests;

import com.rest.springbootemployee.dao.EmployeeRepository;
import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.service.EmployeeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)

public class EmployeeServiceTests {


    @Mock
    EmployeeRepository employeeRepository;
    @InjectMocks
    EmployeeService employeeService;

    List<Employee> prepareEmployeeList=new ArrayList<>();

    @BeforeEach
    public void setUp() {

        for (int i=1;i<=2;i++){
            Employee employee=new Employee(i,"Sally"+i,22,"男",10000);
            prepareEmployeeList.add(employee);
        }
        Mockito.when(employeeRepository.getAll()).thenReturn(prepareEmployeeList);
//        MockitoAnnotations.initMocks(this);
//        employeeRepository.setEmployeeList(prepareEmployeeList); // 这里不会走里面的
//        System.out.println("emeeee" + employeeRepository.employeeList);
        System.out.println("----测试开始----");
    }
    @Test
    public void should_return_all_employee_when_findAll_given_nothing() {
        //given
        //when
        Mockito.when(employeeRepository.getAll()).thenReturn(prepareEmployeeList);
        List<Employee> employeeList=employeeService.findAll();
        //then
        Assertions.assertEquals(prepareEmployeeList,employeeList);
    }

    @Test
    public void should_update_only_age_and_salary_when_update_given_employee() {
        //given
        //given

        Employee em=new Employee(2,"Sally"+2,22,"男",10000);
        Employee prepareEmployee=new Employee(2,"Sally"+2,20,"女",12000);
        //when
        Mockito.when(employeeRepository.findById(2)).thenReturn(em);
        Employee employee=employeeService.updateEmployee(2,prepareEmployee);
        //then
        Assertions.assertEquals(prepareEmployee.getId(),em.getId());
        Assertions.assertEquals(prepareEmployee.getName(),em.getName());
        Assertions.assertEquals(prepareEmployee.getAge(),em.getAge());
        Assertions.assertEquals(prepareEmployee.getGender(),em.getGender());
        Assertions.assertEquals(prepareEmployee.getSalary(),em.getSalary());
        // TODO 这里有疑问
//        Assertions.assertEquals(prepareEmployeeList.get(2).getId(),em.getId());
//        Assertions.assertEquals(prepareEmployeeList.get(2).getName(),em.getName());
//        Assertions.assertEquals(prepareEmployeeList.get(2).getAge(),em.getAge());
//        Assertions.assertEquals(prepareEmployeeList.get(2).getGender(),em.getGender());
//        Assertions.assertEquals(prepareEmployeeList.get(2).getSalary(),em.getSalary());


    }
    @Test
    public void should_return_insert_employee_when_insertEmployee_given_employee() {
        //given
        //given
        Employee prepareEmployee=new Employee(2,"Sally"+2,20,"女",12000);
        //when

        Mockito.when(employeeRepository.insertEmployee(prepareEmployee)).thenReturn(prepareEmployee);
        Employee employee=employeeService.insertEmployee(prepareEmployee);
        //then
        Assertions.assertEquals(prepareEmployee.getId(),employee.getId());
        Assertions.assertEquals(prepareEmployee.getName(),employee.getName());
        Assertions.assertEquals(prepareEmployee.getAge(),employee.getAge());
        Assertions.assertEquals(prepareEmployee.getGender(),employee.getGender());
        Assertions.assertEquals(prepareEmployee.getSalary(),employee.getSalary());

    }


    @Test
    public void should_return_delete_employee_when_deleteEmployee_given_id() {
        //given
        //given
        Integer id=2;
        //when
        Employee returnEmployee=new Employee(2,"Sally"+2,22,"男",10000);
        Mockito.when(employeeRepository.deleteEmployee(id)).thenReturn(returnEmployee);
        Employee employee=employeeService.deleteEmployee(2);
        //then
        Assertions.assertEquals(returnEmployee.getId(),employee.getId());
        Assertions.assertEquals(returnEmployee.getName(),employee.getName());
        Assertions.assertEquals(returnEmployee.getAge(),employee.getAge());
        Assertions.assertEquals(returnEmployee.getGender(),employee.getGender());
        Assertions.assertEquals(returnEmployee.getSalary(),employee.getSalary());

    }



    @AfterEach
    void after() {
        System.out.println("----test?----");
    }

}
