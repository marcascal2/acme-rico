package org.springframework.samples.acmerico.web;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.acmerico.configuration.SecurityConfiguration;
import org.springframework.samples.acmerico.model.Employee;
import org.springframework.samples.acmerico.model.User;
import org.springframework.samples.acmerico.service.AuthoritiesService;
import org.springframework.samples.acmerico.service.EmployeeService;
import org.springframework.samples.acmerico.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.Arrays;

@WebMvcTest(controllers = EmployeeController.class,
				excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
				excludeAutoConfiguration = SecurityConfiguration.class)
public class EmployeeControllerTest {
	
	private static Employee employee = new Employee();
	private static User user = new User();
	
	@MockBean
	private EmployeeService employeeService;
	
    @MockBean
    private UserService     UserService;

    @MockBean
    private AuthoritiesService authoritiesService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@BeforeAll
	static void setUp() {
		user.setUsername("user");
		user.setPassword("pass");
		user.setEnabled(true);
		
		employee.setId(1);
		employee.setFirstName("Jose");
		employee.setLastName("Garcia Dorado");
		employee.setSalary(2000.0);
		employee.setUser(user);
	}

	@WithMockUser(value = "spring")
    @Test
    void testInitCreationForm() throws Exception{
		mockMvc.perform(get("/employees/new"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("employee"))
			.andExpect(view().name("employees/createOrUpdateEmployeeForm"))
			.andExpect(status().is2xxSuccessful());
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationForm() throws Exception{
		mockMvc.perform(post("/employees/new")
		    .param("firstName", "Jose")
		    .param("lastName", "Garcia Dorado")
			.with(csrf())
		    .param("salary", "2000.0"))
			.andExpect(status().is2xxSuccessful());
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormHasErrors() throws Exception{
		mockMvc.perform(post("/employees/new")
		    .param("firstName", "Jose")
			.with(csrf()))
			.andExpect(status().isOk())
			.andExpect(model().attributeHasErrors("employee"))
			.andExpect(model().attributeHasFieldErrors("employee", "lastName"))
			.andExpect(model().attributeHasFieldErrors("employee", "salary"))
			.andExpect(view().name("employees/createOrUpdateEmployeeForm"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testinitFindForm() throws Exception{
		mockMvc.perform(get("/employees/find"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("employee"))
			.andExpect(view().name("employees/findEmployees"))
			.andExpect(status().is2xxSuccessful());
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testProcessFindFormHasEmployee() throws Exception{
		when(this.employeeService.findEmployeeByLastName(employee.getLastName())).thenReturn(Arrays.asList(employee));
		
		mockMvc.perform(get("/employees"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("selections"))
			.andExpect(view().name("employees/employeesList"))
			.andExpect(status().is2xxSuccessful());
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testProcessFindFormNoHasEmployee() throws Exception{
		when(this.employeeService.findEmployeeByLastName("jaja")).thenReturn(Arrays.asList());

		mockMvc.perform(get("/employees"))
			.andExpect(status().isOk())
			.andExpect(model().hasErrors())
			.andExpect(view().name("employees/findEmployees"))
			.andExpect(status().is2xxSuccessful());
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testInitUpdateEmployeeForm() throws Exception{
		when(this.employeeService.findEmployeeById(employee.getId())).thenReturn(employee);
		
		mockMvc.perform(get("/employees/{employeeId}/edit", employee.getId()))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("employee"))
			.andExpect(model().attribute("employee", hasProperty("firstName", is("Jose"))))
			.andExpect(model().attribute("employee", hasProperty("lastName", is("Garcia Dorado"))))
			.andExpect(model().attribute("employee", hasProperty("salary", is(2000.0))))
			.andExpect(view().name("employees/createOrUpdateEmployeeForm"))
			.andExpect(status().is2xxSuccessful());
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateEmployeeForm() throws Exception{
		mockMvc.perform(post("/employees/{employeeId}/edit", employee.getId())
				.with(csrf())
				.param("firstName", "Jose")
				.param("lastName", "Garcia Dorado")
				.param("salary", "1500.0")
				.param("user.username", "user")
				.param("user.password", "pass"))
			.andExpect(status().isFound())
			.andExpect(view().name("redirect:/employees/{employeeId}"))
			.andExpect(status().is3xxRedirection());
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdateEmployeeFormHasErrors() throws Exception{
		mockMvc.perform(post("/employees/{employeeId}/edit", employee.getId())
				.with(csrf())
				.param("firstName", "Jose")
				.param("lastName", "Garcia Dorado"))
			.andExpect(status().isOk())
			.andExpect(model().attributeHasErrors("employee"))
			.andExpect(model().attributeHasFieldErrors("employee", "salary"))
			.andExpect(view().name("employees/createOrUpdateEmployeeForm"))
			.andExpect(status().is2xxSuccessful());
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testShowEmployee() throws Exception{
		when(this.employeeService.findEmployeeById(employee.getId())).thenReturn(employee);
		
		mockMvc.perform(get("/employees/{employeeId}", employee.getId()))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("employee"))
			.andExpect(model().attribute("employee", hasProperty("firstName", is("Jose"))))
			.andExpect(model().attribute("employee", hasProperty("lastName", is("Garcia Dorado"))))
			.andExpect(model().attribute("employee", hasProperty("salary", is(2000.0))))
			.andExpect(view().name("employees/employeesDetails"))
			.andExpect(status().is2xxSuccessful());
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testProcessInitPersonalDataForm() throws Exception{
		when(this.employeeService.findEmployeeByUserName(user.getUsername())).thenReturn(employee);
		
		mockMvc.perform(get("/personalDataEmployee/{name}", user.getUsername()))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("employee"))
			.andExpect(model().attribute("employee", hasProperty("firstName", is("Jose"))))
			.andExpect(model().attribute("employee", hasProperty("lastName", is("Garcia Dorado"))))
			.andExpect(model().attribute("employee", hasProperty("salary", is(2000.0))))
			.andExpect(view().name("employees/employeesDetails"))
			.andExpect(status().is2xxSuccessful());
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testInitUpdatePersonalDataForm() throws Exception{
		when(this.employeeService.findEmployeeById(employee.getId())).thenReturn(employee);
		
		mockMvc.perform(get("/personalDataEmployee/{employeeId}/edit", employee.getId()))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("employee"))
			.andExpect(model().attribute("employee", hasProperty("firstName", is("Jose"))))
			.andExpect(model().attribute("employee", hasProperty("lastName", is("Garcia Dorado"))))
			.andExpect(model().attribute("employee", hasProperty("salary", is(2000.0))))
			.andExpect(view().name("employees/createOrUpdateEmployeeForm"))
			.andExpect(status().is2xxSuccessful());
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdatePersonalDataForm() throws Exception{
		mockMvc.perform(post("/personalDataEmployee/{employeeId}/edit", employee.getId())
				.with(csrf())
				.param("firstName", "Jose")
				.param("lastName", "Garcia Dorado")
				.param("salary", "1500.0")
				.param("user.username", "user")
				.param("user.password", "pass"))
			.andExpect(status().isFound())
			.andExpect(model().hasNoErrors())
			.andExpect(view().name("redirect:/"))
			.andExpect(status().is3xxRedirection());
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testProcessUpdatePersonalDataFormHasErrors() throws Exception{
		mockMvc.perform(post("/personalDataEmployee/{employeeId}/edit", employee.getId())
				.with(csrf())
				.param("firstName", "Jose")
				.param("lastName", "Garcia Dorado"))
			.andExpect(status().isOk())
			.andExpect(model().attributeHasErrors("employee"))
			.andExpect(model().attributeHasFieldErrors("employee", "salary"))
			.andExpect(view().name("employees/createOrUpdateEmployeeForm"))
			.andExpect(status().is2xxSuccessful());
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testDeleteEmployee() throws Exception{
		mockMvc.perform(get("/employees/{employeeId}/delete", employee.getId()))
			.andExpect(status().isFound())
			.andExpect(view().name("redirect:/employees"))
			.andExpect(status().is3xxRedirection());
	}

}
