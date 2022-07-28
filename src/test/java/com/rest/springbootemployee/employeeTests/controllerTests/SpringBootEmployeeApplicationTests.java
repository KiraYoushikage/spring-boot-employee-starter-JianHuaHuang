package com.rest.springbootemployee.employeeTests.controllerTests;

import com.rest.springbootemployee.SpringBootEmployeeApplication;
import com.rest.springbootemployee.dao.CompanyJpaRepository;
import com.rest.springbootemployee.dao.EmployeeJpaRepository;
import com.rest.springbootemployee.dao.impl.EmployeeRepository;
import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;

@SpringBootTest(classes = SpringBootEmployeeApplication.class)
@AutoConfigureMockMvc

@ActiveProfiles("test")
class SpringBootEmployeeApplicationTests {
    @Autowired
    MockMvc client;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    EmployeeJpaRepository employeeJpaRepository;

    @Autowired
    CompanyJpaRepository companyJpaRepository;


    List<Company> prepareCompanyList = new ArrayList<>();

    List<Employee> prepareEmployeeList = new ArrayList<>();

    @BeforeEach
    public void initDatabase() {

        System.out.println("-------initCompanyDatabase----------");
        for (int i = 1; i <= 10; i++) {
            Company company = new Company();
            company.setId(i);
            company.setCompanyName("company" + i);
            prepareCompanyList.add(company);
        }
        companyJpaRepository.saveAll(prepareCompanyList);
        System.out.println(companyJpaRepository.findAll());



        System.out.println("-------initEmployeeDatabase----------");
        for (int i = 1, j = 1; i <= 10; i++) {
            for (int k = 0; k < 5; k++, j++) {
                Employee employee = new Employee();
                employee.setName("employee" + j);
                employee.setAge(20);
                employee.setGender((j & 1) == 0 ? "女" : "男");
                employee.setSalary(10000);
                employee.setCompanyId(i);
                prepareEmployeeList.add(employee);
            }
        }
        employeeJpaRepository.saveAll(prepareEmployeeList);
        System.out.println(employeeJpaRepository.findAll());
    }


    @Test
    void should_get_all_employee_when_get_findAll_given_nothing() throws Exception {
        //given
        //when
        client.perform(MockMvcRequestBuilders.get("/employees")).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(50)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("employee1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value(20))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].gender").value("男"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].salary").value(10000));

        //then

    }


    @Test
    void should_get_special_employee_when_get_findById_given_Id() throws Exception {
        //given
        //when
        client.perform(MockMvcRequestBuilders.get("/employees/{id}", 2)).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("employee2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(20))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("女"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(10000));
        //then

    }


    @Test
    void should_get_special_gender_employee_when_get_findByGender_given_gender() throws Exception {
        //given
        //when
        client.perform(MockMvcRequestBuilders.get("/employees")
                        .param("gender", "女")
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(25)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].id", everyItem(isA(Integer.class))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].name", everyItem(matchesPattern("employee\\d+"))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].age", everyItem(is(20))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].gender", everyItem(is("女"))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].salary", everyItem(is(10000))));

        //then

    }


    @Test
    void should_get_new_employee_when_post_insertEmployee_given_a_new_Employee() throws Exception {
        //given
        String json = "{\n" +
                "    \"name\": \"K\",\n" +
                "    \"age\": 20,\n" +
                "    \"gender\": \"男\",\n" +
                "    \"salary\": 10000 \n" +
                "}";
        //when
        client.perform(MockMvcRequestBuilders.post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("K"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(20))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("男"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(10000));
        //then
    }
//


    @Test
    void should_get_special_page_employeeList_when_get_findByPage_given_page_and_pageSize() throws Exception {
        //given


        //when
        client.perform(MockMvcRequestBuilders.get("/employees")
                        .param("page", "1")
                        .param("pageSize", "5")
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(5)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[*].id",everyItem(isA(Integer.class))))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[*].name", everyItem(matchesPattern("employee\\d+"))))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[*].age", everyItem(is(20))))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[*].gender", everyItem(matchesPattern("[男女]"))))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[*].salary", everyItem(is(10000))));
        //then
    }


    @Test
    void should_get_special_employee_when_delete_deleteEmployee_given_id() throws Exception {
        //given


        //TODO 随机数可以测试范围
        //when
        client.perform(MockMvcRequestBuilders.delete("/employees/{id}", 1)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
        List<Employee> employeeList = employeeJpaRepository.findAll();
        //then
        Assertions.assertEquals(49, employeeList.size());
    }

    //
    @Test
    void should_get_before_employee_when_put_updateEmployee_given_and_employ() throws Exception {
        //given

        String updateEmployeeJson = "{\n" +
                "    \"id\": 5,\n" +
                "    \"name\": \"Sally5\",\n" +
                "    \"age\": 22,\n" +
                "    \"gender\": \"男\",\n" +
                "    \"salary\": 10000 \n" +
                "}";
        //when
        client.perform(MockMvcRequestBuilders.put("/employees/{id}", 5)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateEmployeeJson)
                ).andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Sally5"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(22))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("男"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(10000));
        //then
        Optional<Employee> employeeOptional = employeeJpaRepository.findById(5);
        Assertions.assertNotNull(employeeOptional.orElse(null));
        Assertions.assertEquals(5, employeeOptional.orElseGet(null).getId());
        Assertions.assertEquals("Sally5", employeeOptional.orElseGet(null).getName());
        Assertions.assertEquals("男", employeeOptional.orElseGet(null).getGender());
        Assertions.assertEquals(10000, employeeOptional.orElseGet(null).getSalary());
        Assertions.assertEquals(22, employeeOptional.orElseGet(null).getAge());

    }

    @Test
    public void should_return_employee_not_found_when_findById_given_not_exists_id() throws Exception {
        //given
        //when
        client.perform(MockMvcRequestBuilders.get("/employees/{id}", 100))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(404))
                .andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("EmployeeNotFound"));
        //then
    }

}
