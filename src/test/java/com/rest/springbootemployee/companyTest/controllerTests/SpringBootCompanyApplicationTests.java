package com.rest.springbootemployee.companyTest.controllerTests;


import com.rest.springbootemployee.dao.CompanyRepository;

import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;

@SpringBootTest
@AutoConfigureMockMvc
public class SpringBootCompanyApplicationTests {
    @Autowired
    MockMvc client;
    @Autowired
    CompanyRepository companyRepository;

    @BeforeEach
    public void clearEmployList() {
        companyRepository.clearAll();
    }

    @Test
    public void should_get_all_employee_when_get_findAll_given_nothing() throws Exception {
        //given
        companyRepository.insertCompany(new Company(1, "company1", new Employee(1, "Sally", 22, "男", 10000)));
        //when
        client.perform(MockMvcRequestBuilders.get("/company")).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].companyName").value("company1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employees[0].id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employees[0].name").value("Sally"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employees[0].age").value(22))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employees[0].gender").value("男"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employees[0].salary").value(10000));

        //then


    }

    @Test
    void should_get_new_employee_when_post_insertEmployee_given_a_new_Employee() throws Exception {
        //given
        String json="{\n" +
                "    \"id\": 5,\n" +
                "    \"companyName\": \"company5\",\n" +
                "    \"employees\": [\n" +
                "        {\n" +
                "            \"id\": 21,\n" +
                "            \"name\": \"employee21\",\n" +
                "            \"age\": 43,\n" +
                "            \"gender\": \"男\",\n" +
                "            \"salary\": 18082\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 22,\n" +
                "            \"name\": \"employee22\",\n" +
                "            \"age\": 54,\n" +
                "            \"gender\": \"女\",\n" +
                "            \"salary\": 13211\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 23,\n" +
                "            \"name\": \"employee23\",\n" +
                "            \"age\": 22,\n" +
                "            \"gender\": \"男\",\n" +
                "            \"salary\": 14927\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 24,\n" +
                "            \"name\": \"employee24\",\n" +
                "            \"age\": 29,\n" +
                "            \"gender\": \"女\",\n" +
                "            \"salary\": 10897\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 25,\n" +
                "            \"name\": \"employee25\",\n" +
                "            \"age\": 54,\n" +
                "            \"gender\": \"男\",\n" +
                "            \"salary\": 10115\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        //when
        client.perform(MockMvcRequestBuilders.post("/company")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.companyName").value("company5"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees[0].id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees[0].name").value("employee21"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees[0].age").value(43))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees[0].gender").value("男"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees[0].salary").value(18082));
        //then
        List<Company> companyList =companyRepository.getAll();
        Assertions.assertNotNull(companyList);
        MatcherAssert.assertThat(companyList,hasSize(1));
        Assertions.assertEquals(companyList.get(0).getCompanyName(),"company5");
        Assertions.assertEquals(companyList.get(0).getEmployees().get(0).getName(),"employee21");
        Assertions.assertEquals(companyList.get(0).getEmployees().get(0).getAge(),43);
        Assertions.assertEquals(companyList.get(0).getEmployees().get(0).getGender(),"男");
        Assertions.assertEquals(companyList.get(0).getEmployees().get(0).getSalary(),18082);

    }
    @Test
    void should_get_special_employee_when_get_findById_given_Id() throws Exception {
        //given
        companyRepository.insertCompany(new Company(1, "company1", new Employee(1, "Sally", 22, "男", 10000)));
        //when
        client.perform(MockMvcRequestBuilders.get("/company/{id}",1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.companyName").value("company1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees[0].id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees[0].name").value("Sally"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees[0].age").value(22))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees[0].gender").value("男"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees[0].salary").value(10000));

        //then

    }


    @Test
    void should_get_special_page_employeeList_when_get_findByPage_given_page_and_pageSize() throws Exception {
        //given

        List<Company> companyList=companyRepository.getAll();
        for (int i = 1, j=1 ; i <=10 ; i++) {
            Company company=new Company(i,"company"+i,new ArrayList<>());
            for(int k=0;k<5;k++,j++){
                company.getEmployees().add(new Employee(j, "employee"+j,(int)(Math.random()*35) + 20, (j&1)==0 ? "女": "男", (int)(Math.random()*15000) + 10000));
            }
            companyList.add(company);
        }
        //when
        client.perform(MockMvcRequestBuilders.get("/company")
                        .param("page", "1")
                        .param("pageSize","5")
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",hasSize(5)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNumber());
        //then
    }
//
//
    @Test
    void should_get_special_employee_when_delete_deleteEmployee_given_id() throws Exception {
        //given


        List<Company> companyList=companyRepository.getAll();
        for (int i = 1, j=1 ; i <=10 ; i++) {
            Company company=new Company(i,"company"+i,new ArrayList<>());
            for(int k=0;k<5;k++,j++){
                company.getEmployees().add(new Employee(j, "employee"+j,(int)(Math.random()*35) + 20, (j&1)==0 ? "女": "男", (int)(Math.random()*15000) + 10000));
            }
            companyList.add(company);
        }
        Company deleteCompany=companyList.get(0);
        //TODO 随机数可以测试范围
        //when
        client.perform(MockMvcRequestBuilders.delete("/company/{id}",1)
                ).andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
        //then
        // todo 其实还要验证list里面到底有没有被修改
        Assertions.assertEquals(false,companyList.contains(deleteCompany));
    }

    @Test
    void should_get_before_employee_when_put_updateEmployee_given_and_employ() throws Exception {
        //given

        List<Company> companyList=companyRepository.getAll();
        for (int i = 1, j=1 ; i <=10 ; i++) {
            Company company=new Company(i,"company"+i,new ArrayList<>());
            for(int k=0;k<5;k++,j++){
                company.getEmployees().add(new Employee(j, "employee"+j,(int)(Math.random()*35) + 20, (j&1)==0 ? "女": "男", (int)(Math.random()*15000) + 10000));
            }
            companyList.add(company);
        }
        String json="{\n" +
                "    \"id\": 5,\n" +
                "    \"companyName\": \"company55555\",\n" +
                "    \"employees\": [\n" +
                "        {\n" +
                "            \"id\": 21,\n" +
                "            \"name\": \"employee21\",\n" +
                "            \"age\": 43,\n" +
                "            \"gender\": \"男\",\n" +
                "            \"salary\": 18082\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 22,\n" +
                "            \"name\": \"employee22\",\n" +
                "            \"age\": 54,\n" +
                "            \"gender\": \"女\",\n" +
                "            \"salary\": 13211\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 23,\n" +
                "            \"name\": \"employee23\",\n" +
                "            \"age\": 22,\n" +
                "            \"gender\": \"男\",\n" +
                "            \"salary\": 14927\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 24,\n" +
                "            \"name\": \"employee24\",\n" +
                "            \"age\": 29,\n" +
                "            \"gender\": \"女\",\n" +
                "            \"salary\": 10897\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 25,\n" +
                "            \"name\": \"employee25\",\n" +
                "            \"age\": 54,\n" +
                "            \"gender\": \"男\",\n" +
                "            \"salary\": 10115\n" +
                "        }\n" +
                "    ]\n" +
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
