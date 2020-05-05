package org.springframework.samples.acmerico.e2e;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.acmerico.web.EmployeeController;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
//@TestPropertySource(locations = "classpath:application-mysql.properties")
public class EmployeeControllerIntegrationTests {

	private static final Integer TEST_EMPLOYEE_ID = 1;

	@Autowired
	private EmployeeController employeeController;

	@Autowired
	private MockMvc mockMvc;
	
	@WithMockUser(username = "director1", authorities = { "director" })
    @Test
    void testInitCreationForm() throws Exception{
		mockMvc.perform(get("/employees/new"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("employee"))
			.andExpect(view().name("employees/createOrUpdateEmployeeForm"))
			.andExpect(status().is2xxSuccessful());
	}
	
	@WithMockUser(username = "director1", authorities = { "director" })
	@Test
	void testProcessCreationForm() throws Exception{
		mockMvc.perform(post("/employees/new")
		    .param("firstName", "Jose")
		    .param("lastName", "Garcia Dorado")
			.with(csrf())
		    .param("salary", "2000.0"))
			.andExpect(status().is2xxSuccessful());
	}
	
	@WithMockUser(username = "director1", authorities = { "director" })
	@Test
	void testProcessCreationFormHasErrors() throws Exception{
		mockMvc.perform(post("/employees/new")
			.param("firstName", "worker1"))
			.andExpect(status().is4xxClientError());
	}
	
	@WithMockUser(username = "director1", authorities = { "director" })
	@Test
	void testinitFindForm() throws Exception{
		mockMvc.perform(get("/employees/find"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("employee"))
			.andExpect(view().name("employees/findEmployees"))
			.andExpect(status().is2xxSuccessful());
	}
	
	@WithMockUser(username = "director1", authorities = { "director" })
	@Test
	void testProcessFindFormHasEmployee() throws Exception{		
		mockMvc.perform(get("/employees")
				.with(csrf())
				.param("lastName", "Garcia Prado"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("selections"))
			.andExpect(view().name("employees/employeesList"))
			.andExpect(status().is2xxSuccessful());
	}
	
	@WithMockUser(username = "director1", authorities = { "director" })
	@Test
	void testProcessFindFormNoHasEmployee() throws Exception{
		mockMvc.perform(get("/employees").with(csrf()).param("lastName", "jaja"))
		.andExpect(model().attributeHasErrors("employee")).andExpect(status().isOk())
		.andExpect(model().hasErrors()).andExpect(view().name("employees/findEmployees"))
		.andExpect(status().is2xxSuccessful());
	}
}
