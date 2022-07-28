package com.rest.springbootemployee.companyTest.controllerTests;


import com.rest.springbootemployee.dao.CompanyJpaRepository;
import com.rest.springbootemployee.dao.EmployeeJpaRepository;
import com.rest.springbootemployee.dao.impl.CompanyRepository;

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


import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc

@ActiveProfiles("test")
public class SpringBootCompanyApplicationTests {
    @Autowired
    MockMvc client;
    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    CompanyJpaRepository companyJpaRepository;

    @Autowired
    EmployeeJpaRepository employeeJpaRepository;
    List<Company> prepareCompanyList = new ArrayList<>();

    List<Employee> prepareEmployeeList = new ArrayList<>();


    @BeforeEach
    public void initDataBase() {
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
                employee.setId(null);
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
    public void should_get_all_company_when_get_findAll_given_nothing() throws Exception {
        //given
        //when
        client.perform(MockMvcRequestBuilders.get("/company")).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(10)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].id", everyItem(isA(Integer.class))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].companyName", everyItem(matchesPattern("company\\d+"))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].employees", hasSize(10)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].employees[*]", hasSize(50)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].employees[*].name", everyItem(matchesPattern("employee\\d+"))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].employees[*].age", everyItem(is(20))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].employees[*].gender", everyItem(matchesPattern("[男女]"))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].employees[*].salary", everyItem(is(10000))));

        //then


    }


    @Test
    void should_get_special_company_when_get_findById_given_Id() throws Exception {
        //given
        //when
        client.perform(MockMvcRequestBuilders.get("/company/{id}",1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.companyName").value("company1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees", hasSize(5)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees[*].name", everyItem(matchesPattern("employee\\d+"))))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees[*].age", everyItem(is(20))))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees[*].gender", everyItem(matchesPattern("[男女]"))))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees[*].salary", everyItem(is(10000))));

        //then

    }

    @Test
    void should_get_special_company_when_findCompanyById_given_id() throws Exception {
        //given
        //when
        client.perform(MockMvcRequestBuilders.get("/company/{id}/employees",1)
                ).andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].name", everyItem(matchesPattern("employee\\d+"))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].age", everyItem(is(20))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].gender", everyItem(matchesPattern("[男女]"))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].salary", everyItem(is(10000))));
        //then
    }
    @Test
    void should_get_special_page_CompanyList_when_get_findByPage_given_page_and_pageSize() throws Exception {
        //given
        //when
        client.perform(MockMvcRequestBuilders.get("/company")
                        .param("page", "1")
                        .param("pageSize","5")
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(5)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[*].id",everyItem(isA(Integer.class))))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[*].companyName", everyItem(matchesPattern("company\\d+"))))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[*].employees[*].id",everyItem(isA(Integer.class))))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[*].employees[*].name", everyItem(matchesPattern("employee\\d+"))))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[*].employees[*].age", everyItem(is(20))))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[*].employees[*].gender", everyItem(matchesPattern("[男女]"))))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[*].employees[*].salary", everyItem(is(10000))));
        //then
    }

        @Test
    void should_get_special_company_when_delete_deleteCompany_given_id() throws Exception {
        //given

        //TODO 随机数可以测试范围
        //when
        client.perform(MockMvcRequestBuilders.delete("/company/{id}",1)
                ).andExpect(MockMvcResultMatchers.status().isNoContent());
        List<Company> companyList=companyJpaRepository.findAll();
        //then
        Assertions.assertNotNull(companyList);
        Assertions.assertEquals(9, companyList.size());
    }


    @Test
    void should_get_new_company_when_post_insertCompany_given_a_new_company() throws Exception {
        //given
        String json="{\n" +
                "    \"id\": 11,\n" +
                "    \"companyName\": \"company555555\"\n" +
                "}";
        //when
        client.perform(MockMvcRequestBuilders.post("/company")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.companyName").value("company555555"));
        //then

    }




    @Test
    void should_get_Company_when_put_updateCompany_given_and_company_and_id() throws Exception {
        //given
        String json="{\n" +
                "    \"id\": 5,\n" +
                "    \"companyName\": \"company55555\"\n" +
                "}";
        //when
        client.perform(MockMvcRequestBuilders.put("/company/{id}",5)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                ).andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.companyName").value("company55555"));
        //then
    }


}
