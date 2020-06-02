package org.springframework.samples.acmerico.web;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.acmerico.configuration.SecurityConfiguration;
import org.springframework.samples.acmerico.model.Client;
import org.springframework.samples.acmerico.model.User;
import org.springframework.samples.acmerico.service.AuthoritiesService;
import org.springframework.samples.acmerico.service.ClientService;
import org.springframework.samples.acmerico.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.context.annotation.FilterType;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.hamcrest.Matchers.*;

@WebMvcTest(controllers = ClientController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)

public class ClientControllerTest {

	private static final Integer TEST_CLIENT_ID = 1;

	private static final String TEST_CLIENT_USER = "client1";

	private static final String TEST_CLIENT_USER2 = "client2";

	private static final String TEST_CLIENT_LASTNAME = "Ruiz";

	private static final String TEST_CLIENT_LASTNAME2 = "Muñiz";

	@MockBean
	private ClientService clientService;

	@MockBean
	private UserService UserService;

	@MockBean
	private AuthoritiesService authoritiesService;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BindingResult bindingResult;

	private Client client1;
	private Client client2;
	private User user;
	private User user2;

	@BeforeEach
	void setup() {

		MockitoAnnotations.initMocks(this);

		user = new User();
		user2 = new User();

		user.setUsername(TEST_CLIENT_USER);
		user.setPassword("client1");
		user.setEnabled(true);

		user2.setUsername(TEST_CLIENT_USER2);
		user2.setPassword("client2");
		user2.setEnabled(true);

		client1 = new Client();
		client2 = new Client();

		Collection<Client> collection = new ArrayList<Client>();
		Collection<Client> collection2 = new ArrayList<Client>();

		final LocalDate bithday = LocalDate.of(1998, 11, 27);
		final LocalDate lEmpDate = LocalDate.of(2010, 01, 22);

		client1.setId(TEST_CLIENT_ID);
		client1.setFirstName("Javier");
		client1.setLastName(TEST_CLIENT_LASTNAME);
		client1.setAddress("Gordal");
		client1.setBirthDate(bithday);
		client1.setCity("Sevilla");
		client1.setMaritalStatus("married");
		client1.setSalaryPerYear(300000.);
		client1.setAge(21);
		client1.setJob("student");
		client1.setLastEmployDate(lEmpDate);
		client1.setUser(user);

		client2.setId(TEST_CLIENT_ID);
		client2.setFirstName("Juan");
		client2.setLastName(TEST_CLIENT_LASTNAME2);
		client2.setAddress("Gordal");
		client2.setBirthDate(bithday);
		client2.setCity("Sevilla");
		client2.setMaritalStatus("single but whole");
		client2.setSalaryPerYear(300000.);
		client2.setAge(21);
		client2.setJob("student");
		client2.setLastEmployDate(lEmpDate);
		client2.setUser(user2);

		collection.add(client1);
		collection2.add(client2);
		collection2.add(client1);

		when(this.clientService.findClientById(TEST_CLIENT_ID)).thenReturn(client1);
		when(this.clientService.findClientByUserName(TEST_CLIENT_USER)).thenReturn(client1);
		when(this.clientService.findClientByLastName(TEST_CLIENT_LASTNAME)).thenReturn(collection);
		when(this.clientService.findClientByLastName(TEST_CLIENT_LASTNAME2)).thenReturn(collection2);

	}

	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/clients/new")).andExpect(status().isOk()).andExpect(model().attributeExists("client"))
				.andExpect(view().name("clients/createOrUpdateClientForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/clients/new").param("firstName", "Javier").param("lastName", "Ruiz")
				.param("address", "Gordal").param("birthDate", "1998/11/27").param("city", "Sevilla")
				.param("maritalStatus", "single but whole").param("salaryPerYear", "300000.").param("age", "21")
				.param("job", "student").param("lastEmployDate", "2010/01/22")
				.param("user.username", "Username23").param("user.password", "values").with(csrf()))
				.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/clients/null"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/clients/new").with(csrf()).param("firstName", "Javier").param("lastName", "Ruiz")
				.param("address", "Gordal")
				// .param("birthDate", "1998/11/27")
				.param("user.username", "client1")
				.param("user.password", "client1")
				.param("city", "Sevilla").param("maritalStatus", "single but whole").param("salaryPerYear", "300000.")
				.param("lastEmployDate", "2010-01-22")).andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("client"))
				.andExpect(model().attributeHasFieldErrors("client", "age"))
				.andExpect(model().attributeHasFieldErrors("client", "job"))
				.andExpect(model().attributeHasFieldErrors("client", "birthDate"))
				.andExpect(view().name("clients/createOrUpdateClientForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testInitFindForm() throws Exception {
		mockMvc.perform(get("/clients/find")).andExpect(status().isOk()).andExpect(model().attributeExists("client"))
				.andExpect(view().name("clients/findClients"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessFindFormWithoutClients() throws Exception {
		mockMvc.perform(get("/clients")).andExpect(model().attributeExists("client"))
				.andExpect(model().attribute("client", hasProperty("lastName", is(""))))
				.andExpect(status().is2xxSuccessful()).andExpect(view().name("clients/findClients"));

		verify(clientService).findClientByLastName("");

	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessFindFormWithOneClient() throws Exception {
		mockMvc.perform(get("/clients").with(csrf()).param("lastName", TEST_CLIENT_LASTNAME))
				.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/clients/" + TEST_CLIENT_ID));

		verify(clientService).findClientByLastName(TEST_CLIENT_LASTNAME);
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessFindFormWithMoreClients() throws Exception {
		mockMvc.perform(get("/clients").with(csrf()).param("lastName", TEST_CLIENT_LASTNAME2))
				.andExpect(model().attributeExists("selections")).andExpect(status().is2xxSuccessful())
				.andExpect(view().name("clients/clientsList"));

		verify(clientService).findClientByLastName(TEST_CLIENT_LASTNAME2);
	}

	@WithMockUser(value = "spring")
	@Test
	void testShowClientById() throws Exception {
		mockMvc.perform(get("/clients/{clientId}", TEST_CLIENT_ID)).andExpect(model().attributeExists("client"))
				.andExpect(model().attribute("client", hasProperty("firstName", is("Javier"))))
				.andExpect(model().attribute("client", hasProperty("lastName", is("Ruiz"))))
				.andExpect(model().attribute("client", hasProperty("address", is("Gordal"))))
				.andExpect(model().attribute("client", hasProperty("city", is("Sevilla"))))
				.andExpect(view().name("clients/clientsDetails")).andExpect(status().is2xxSuccessful());

		verify(clientService).findClientById(TEST_CLIENT_ID);

	}

	@WithMockUser(value = "spring")
	@Test
	void initUpdateFormClient() throws Exception {
		final LocalDate bithday = LocalDate.of(1998, 11, 27);
		final LocalDate lEmpDate = LocalDate.of(2010, 01, 22);

		mockMvc.perform(get("/clients/{clientId}/edit", TEST_CLIENT_ID)).andExpect(model().attributeExists("client"))
				.andExpect(model().attribute("client", hasProperty("firstName", is("Javier"))))
				.andExpect(model().attribute("client", hasProperty("lastName", is("Ruiz"))))
				.andExpect(model().attribute("client", hasProperty("address", is("Gordal"))))
				.andExpect(model().attribute("client", hasProperty("birthDate", is(bithday))))
				.andExpect(model().attribute("client", hasProperty("city", is("Sevilla"))))
				.andExpect(model().attribute("client", hasProperty("maritalStatus", is("married"))))
				.andExpect(model().attribute("client", hasProperty("salaryPerYear", is(300000.0))))
				.andExpect(model().attribute("client", hasProperty("age", is(21))))
				.andExpect(model().attribute("client", hasProperty("job", is("student"))))
				.andExpect(model().attribute("client", hasProperty("lastEmployDate", is(lEmpDate))))
				.andExpect(view().name("clients/createOrUpdateClientForm"));

		verify(clientService).findClientById(TEST_CLIENT_ID);
	}

	@WithMockUser(value = "spring")
	@Test
	void testUpdateFormClientSuccess() throws Exception {
		mockMvc.perform(post("/clients/{clientId}/edit", TEST_CLIENT_ID)
				.with(csrf())
				.param("firstName", "Javier")
				.param("lastName", "Ruiz")
				.param("address", "Gordal")
				.param("birthDate", "1998/11/27")
				.param("city", "Sevilla")
				.param("maritalStatus", "married")
				.param("salaryPerYear", "300000.")
				.param("age", "21")
				.param("job", "student")
				.param("lastEmployDate", "2010/01/22")
				.param("user.username", "client1")
				.param("user.password", "client1")
				.with(csrf()))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/clients/{clientId}"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testUpdateFormClientHasErrors() throws Exception {
		mockMvc.perform(post("/clients/{clientId}/edit", TEST_CLIENT_ID).with(csrf()).param("firstName", "Javier")
				.param("lastName", "Ruiz").param("address", "Gordal")
				.param("city", "Sevilla").param("maritalStatus", "married").param("salaryPerYear", "300000.")
				.param("lastEmployDate", "2010-01-22")).andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("client"))
				.andExpect(model().attributeHasFieldErrors("client", "age"))
				.andExpect(model().attributeHasFieldErrors("client", "job"))
				.andExpect(model().attributeHasFieldErrors("client", "birthDate"))
				.andExpect(view().name("clients/createOrUpdateClientForm"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testShowPersonalDataByName() throws Exception {
		mockMvc.perform(get("/personalData/{name}", TEST_CLIENT_USER)).andExpect(model().attributeExists("client"))
				.andExpect(model().attribute("client", hasProperty("firstName", is("Javier"))))
				.andExpect(model().attribute("client", hasProperty("lastName", is("Ruiz"))))
				.andExpect(model().attribute("client", hasProperty("address", is("Gordal"))))
				.andExpect(model().attribute("client", hasProperty("city", is("Sevilla"))))
				.andExpect(view().name("clients/clientsDetails")).andExpect(status().is2xxSuccessful());

		verify(clientService).findClientByUserName(TEST_CLIENT_USER);
	}

	@WithMockUser(value = "spring")
	@Test
	void initUpdateFormPersonalDataClient() throws Exception {
		final LocalDate bithday = LocalDate.of(1998, 11, 27);
		final LocalDate lEmpDate = LocalDate.of(2010, 01, 22);
		user = new User();

		user.setUsername(TEST_CLIENT_USER);
		user.setPassword("client1");
		user.setEnabled(true);

		mockMvc.perform(get("/personalData/{clientId}/edit", TEST_CLIENT_ID))
				.andExpect(model().attributeExists("client"))
				.andExpect(model().attribute("client", hasProperty("firstName", is("Javier"))))
				.andExpect(model().attribute("client", hasProperty("lastName", is("Ruiz"))))
				.andExpect(model().attribute("client", hasProperty("address", is("Gordal"))))
				.andExpect(model().attribute("client", hasProperty("birthDate", is(bithday))))
				.andExpect(model().attribute("client", hasProperty("city", is("Sevilla"))))
				.andExpect(model().attribute("client", hasProperty("maritalStatus", is("married"))))
				.andExpect(model().attribute("client", hasProperty("salaryPerYear", is(300000.0))))
				.andExpect(model().attribute("client", hasProperty("age", is(21))))
				.andExpect(model().attribute("client", hasProperty("job", is("student"))))
				.andExpect(model().attribute("client", hasProperty("lastEmployDate", is(lEmpDate))))
				.andExpect(model().attribute("client", hasProperty("user", is(user))))
				.andExpect(view().name("clients/createOrUpdateClientForm"));

		verify(clientService).findClientById(TEST_CLIENT_ID);
	}

	@WithMockUser(value = "spring")
	@Test
	void testUpdateFormPersonalDataSuccess() throws Exception {
		mockMvc.perform(post("/personalData/{clientId}/edit", TEST_CLIENT_ID)
				.param("id", "2")
				.param("firstName", "Javi")
				.param("lastName", "Ruiz")
				.param("address", "Gordal")
				.param("birthDate", "1998/11/27")
				.param("city", "Sevilla")
				.param("maritalStatus", "married")
				.param("salaryPerYear", "300000.")
				.param("age", "21")
				.param("job", "student")
				.param("lastEmployDate", "2010/01/22")
				.param("user.username", "client1")
				.param("user.password", "client1").with(csrf()))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testUpdateFormPersonalDataHasErrors() throws Exception {
		mockMvc.perform(post("/personalData/{clientId}/edit", TEST_CLIENT_ID).param("firstName", "Javier")
				.param("lastName", "Ruiz").param("address", "Gordal")
				.param("city", "Sevilla").param("maritalStatus", "married").param("salaryPerYear", "300000.")
				.param("lastEmployDate", "2010/01/22").param("user.username", "client1")
				.param("user.password", "client1").with(csrf())).andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("client"))
				.andExpect(model().attributeHasFieldErrors("client", "age"))
				.andExpect(model().attributeHasFieldErrors("client", "job"))
				.andExpect(model().attributeHasFieldErrors("client", "birthDate")).andExpect(status().is2xxSuccessful())
				.andExpect(view().name("clients/createOrUpdateClientForm"));
	}

}