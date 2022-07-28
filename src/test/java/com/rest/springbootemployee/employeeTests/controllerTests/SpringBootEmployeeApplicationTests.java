package com.rest.springbootemployee.employeeTests.controllerTests;

import com.rest.springbootemployee.SpringBootEmployeeApplication;
import com.rest.springbootemployee.dao.EmployeeRepository;
import com.rest.springbootemployee.entity.Employee;
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

import  org.hamcrest.MatcherAssert;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;

@SpringBootTest(classes = SpringBootEmployeeApplication.class)
@AutoConfigureMockMvc
class SpringBootEmployeeApplicationTests {
	@Autowired
	MockMvc client;

	@Autowired
	EmployeeRepository employeeRepository;

	@BeforeEach
	public void clearEmployList(){
		employeeRepository.clearAll();
	}
	@Test
	void should_get_all_employee_when_get_findAll_given_nothing() throws Exception {
	    //given
		employeeRepository.insertEmployee(new Employee(1,"Sally",22,"男",10000));
	    //when
		client.perform(MockMvcRequestBuilders.get("/employees")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$",hasSize(1)))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNumber())
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Sally"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value(22))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].gender").value("男"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].salary").value(10000));

		//then

	}

	@Test
	void should_get_new_employee_when_post_insertEmployee_given_a_new_Employee() throws Exception {
		//given
		String json="{\n" +
				"    \"id\": 11,\n" +
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
		List<Employee> employeeList =employeeRepository.getAll();
		Assertions.assertNotNull(employeeList);
		MatcherAssert.assertThat(employeeList,hasSize(2));
		Assertions.assertEquals(employeeList.get(0).getName(),"K");
		Assertions.assertEquals(employeeList.get(0).getAge(),20);
		Assertions.assertEquals(employeeList.get(0).getGender(),"男");
		Assertions.assertEquals(employeeList.get(0).getSalary(),10000);

	}
	@Test
	void should_get_special_employee_when_get_findById_given_Id() throws Exception {
		//given
		employeeRepository.insertEmployee(new Employee(1,"Sally",22,"男",10000));
		//when
		client.perform(MockMvcRequestBuilders.get("/employees/{id}",1)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Sally"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.age").value(22))
				.andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("男"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(10000));

		//then

	}



	@Test
	void should_get_special_gender_employee_when_get_findByGender_given_gender() throws Exception {
		//given
		employeeRepository.insertEmployee(new Employee(1,"Sally",22,"男",10000));
		employeeRepository.insertEmployee(new Employee(2,"Sally2",22,"女",10000));
		employeeRepository.insertEmployee(new Employee(3,"Sally3",22,"男",10000));
		//when
		client.perform(MockMvcRequestBuilders.get("/employees")
						.param("gender", "女")
				).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$",hasSize(1)))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNumber())
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Sally2"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value(22))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].gender").value("女"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].salary").value(10000));

		//then

	}
	@Test
	void should_get_special_page_employeeList_when_get_findByPage_given_page_and_pageSize() throws Exception {
		//given


		for (int i=0;i<10;i++){
			Employee employee=new Employee(i,"Sally"+i,22,"男",10000);
			employeeRepository.insertEmployee(employee);
		}
		//when
		client.perform(MockMvcRequestBuilders.get("/employees")
						.param("page", "1")
						.param("pageSize","5")
				).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$",hasSize(5)))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNumber());
		//then
		//TODO 如何验证这种随机不确定的内容
	}


	@Test
	void should_get_special_employee_when_delete_deleteEmployee_given_id() throws Exception {
		//given


		for (int i=0;i<10;i++){
			Employee employee=new Employee(i,"Sally"+i,22,"男",10000);
			employeeRepository.insertEmployee(employee);
		}
		//TODO 随机数可以测试范围
		//when
		client.perform(MockMvcRequestBuilders.delete("/employees/{id}",1)
				).andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Sally1"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.age").value(22))
				.andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("男"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(10000));
		//then
	}

	@Test
	void should_get_before_employee_when_put_updateEmployee_given_and_employ() throws Exception {
		//given


		for (int i=0;i<10;i++){
			Employee employee=new Employee(i,"Sally"+i,22,"男",10000);
			employeeRepository.insertEmployee(employee);
		}
		String updateEmployeeJson="{\n" +
				"    \"id\": 5,\n" +
				"    \"name\": \"Sally5\",\n" +
				"    \"age\": 22,\n" +
				"    \"gender\": \"男\",\n" +
				"    \"salary\": 10000 \n" +
				"}";
		//when
		client.perform(MockMvcRequestBuilders.put("/employees/{id}",5)
						.contentType(MediaType.APPLICATION_JSON)
						.content(updateEmployeeJson)
				).andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Sally5"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.age").value(22))
				.andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("男"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(10000));
		//then
	}
}
