package org.springframework.samples.acmerico.e2e;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
				.param("lastName", "Pierce"))
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
	
	@WithMockUser(username = "director1", authorities = { "director" })
	@Test
	void testInitUpdateEmployeeForm() throws Exception {

		mockMvc.perform(get("/employees/{employeeId}/edit", TEST_EMPLOYEE_ID)).andExpect(status().isOk())
				.andExpect(model().attributeExists("employee"))
				.andExpect(model().attribute("employee", hasProperty("firstName", is("Margareth"))))
				.andExpect(model().attribute("employee", hasProperty("lastName", is("Murphy"))))
				.andExpect(model().attribute("employee", hasProperty("salary", is(3000.0))))
				.andExpect(view().name("employees/createOrUpdateEmployeeForm")).andExpect(status().is2xxSuccessful());
	}

	@WithMockUser(username = "director1", authorities = { "director" })
	@Test
	void testProcessUpdateEmployeeForm() throws Exception {
		mockMvc.perform(post("/employees/{employeeId}/edit", TEST_EMPLOYEE_ID).with(csrf()).param("firstName", "Margareth")
				.param("lastName", "Murphy").param("salary", "1500.0").param("user.username", "director1")
				.param("user.password", "director1")).andExpect(status().isFound())
				.andExpect(view().name("redirect:/employees/{employeeId}")).andExpect(status().is3xxRedirection());
	}

	@WithMockUser(username = "director1", authorities = { "director" })
	@Test
	void testProcessUpdateEmployeeFormHasErrors() throws Exception {
		mockMvc.perform(post("/employees/{employeeId}/edit", TEST_EMPLOYEE_ID).with(csrf()).param("firstName", "Jose")
				.param("lastName", "Garcia Dorado")).andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("employee"))
				.andExpect(model().attributeHasFieldErrors("employee", "salary"))
				.andExpect(view().name("employees/createOrUpdateEmployeeForm")).andExpect(status().is2xxSuccessful());
	}

	@WithMockUser(username = "director1", authorities = { "director" })
	@Test
	void testShowEmployee() throws Exception {
		mockMvc.perform(get("/employees/{employeeId}", 2)).andExpect(status().isOk())
				.andExpect(model().attributeExists("employee"))
				.andExpect(model().attribute("employee", hasProperty("firstName", is("Thomas"))))
				.andExpect(model().attribute("employee", hasProperty("lastName", is("Pierce"))))
				.andExpect(model().attribute("employee", hasProperty("salary", is(1500.0))))
				.andExpect(view().name("employees/employeesDetails")).andExpect(status().is2xxSuccessful());
	}
	
	@WithMockUser(username = "worker1", authorities = { "worker" })
	@Test
	void testProcessInitPersonalDataForm() throws Exception {
		mockMvc.perform(get("/personalDataEmployee/{name}", "worker1")).andExpect(status().isOk())
				.andExpect(model().attributeExists("employee"))
				.andExpect(model().attribute("employee", hasProperty("firstName", is("Thomas"))))
				.andExpect(model().attribute("employee", hasProperty("lastName", is("Pierce"))))
				.andExpect(model().attribute("employee", hasProperty("salary", is(1500.0))))
				.andExpect(view().name("employees/employeesDetails")).andExpect(status().is2xxSuccessful());
	}

	@WithMockUser(username = "worker1", authorities = { "worker" })
	@Test
	void testInitUpdatePersonalDataForm() throws Exception {
		mockMvc.perform(get("/personalDataEmployee/{employeeId}/edit", 2)).andExpect(status().isOk())
				.andExpect(model().attributeExists("employee"))
				.andExpect(model().attribute("employee", hasProperty("firstName", is("Thomas"))))
				.andExpect(model().attribute("employee", hasProperty("lastName", is("Pierce"))))
				.andExpect(model().attribute("employee", hasProperty("salary", is(1500.0))))
				.andExpect(view().name("employees/createOrUpdateEmployeeForm")).andExpect(status().is2xxSuccessful());
	}
	
	@WithMockUser(username = "worker1", authorities = { "worker" })
	@Test
	void testProcessUpdatePersonalDataForm() throws Exception {
		mockMvc.perform(post("/personalDataEmployee/{employeeId}/edit", 2).with(csrf())
				.param("firstName", "Thomas").param("lastName", "Garcia Prado").param("salary", "3500.0")
				.param("user.username", "worker1").param("user.password", "worker1")).andExpect(status().isFound())
				.andExpect(model().hasNoErrors()).andExpect(view().name("redirect:/"))
				.andExpect(status().is3xxRedirection());
	}

	@WithMockUser(username = "worker2", authorities = { "worker" })
	@Test
	void testProcessUpdatePersonalDataFormHasErrors() throws Exception {
		mockMvc.perform(post("/personalDataEmployee/{employeeId}/edit", 3).with(csrf())
				.param("firstName", "Rafael").param("lastName", "Corchuelo F")).andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("employee"))
				.andExpect(model().attributeHasFieldErrors("employee", "salary"))
				.andExpect(view().name("employees/createOrUpdateEmployeeForm")).andExpect(status().is2xxSuccessful());
	}

	@WithMockUser(username = "director1", authorities = { "director" })
	@Test
	void testDeleteEmployee() throws Exception {
		mockMvc.perform(get("/employees/{employeeId}/delete", 3)).andExpect(status().isFound())
				.andExpect(view().name("redirect:/employees")).andExpect(status().is3xxRedirection());
	}
}
