package org.springframework.samples.acmerico.e2e;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.acmerico.model.User;
import org.springframework.samples.acmerico.web.ClientController;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
//@TestPropertySource(locations = "classpath:application-mysql.properties")
class ClientControllerIntegrationTests {

	private static final Integer TEST_CLIENT_ID = 1;

	@Autowired
	private ClientController clientController;

	@Autowired
	private MockMvc mockMvc;

	@Test
	void testInitCreationForm() throws Exception {
		ModelMap model = new ModelMap();
		String view = clientController.initCreationForm(model);
		assertEquals(view, "clients/createOrUpdateClientForm");
		assertNotNull(model.get("client"));
	}

	@WithMockUser(username = "worker1", authorities = { "worker" })
	@Test
	void testProcessFindFormWithoutClients() throws Exception {
		mockMvc.perform(get("/clients")).andExpect(model().attributeExists("client"))
				.andExpect(model().attribute("client", hasProperty("lastName", is(""))))
				.andExpect(status().is2xxSuccessful()).andExpect(view().name("clients/clientsList"));

	}

	@WithMockUser(username = "worker1", authorities = { "worker" })
	@Test
	void testProcessFindFormWithOneClient() throws Exception {
		mockMvc.perform(get("/clients").with(csrf()).param("lastName", "Franklin"))
				.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/clients/" + TEST_CLIENT_ID));

	}

	@WithMockUser(username = "worker1", authorities = { "worker" })
	@Test
	void testShowClientById() throws Exception {
		mockMvc.perform(get("/clients/{clientId}", TEST_CLIENT_ID)).andExpect(model().attributeExists("client"))
				.andExpect(model().attribute("client", hasProperty("firstName", is("George"))))
				.andExpect(model().attribute("client", hasProperty("lastName", is("Franklin"))))
				.andExpect(model().attribute("client", hasProperty("address", is("110 W. Liberty St."))))
				.andExpect(model().attribute("client", hasProperty("city", is("Madison"))))
				.andExpect(view().name("clients/clientsDetails")).andExpect(status().is2xxSuccessful());

	}

	@WithMockUser(username = "director1", authorities = { "director" })
	@Test
	void initUpdateFormClient() throws Exception {
		final LocalDate bithday = LocalDate.of(1998, 04, 04);
		final LocalDate lEmpDate = LocalDate.of(2018, 06, 06);

		mockMvc.perform(get("/clients/{clientId}/edit", TEST_CLIENT_ID)).andExpect(model().attributeExists("client"))
				.andExpect(model().attribute("client", hasProperty("firstName", is("George"))))
				.andExpect(model().attribute("client", hasProperty("lastName", is("Franklin"))))
				.andExpect(model().attribute("client", hasProperty("address", is("110 W. Liberty St."))))
				.andExpect(model().attribute("client", hasProperty("birthDate", is(bithday))))
				.andExpect(model().attribute("client", hasProperty("city", is("Madison"))))
				.andExpect(model().attribute("client", hasProperty("maritalStatus", is("MARRIED"))))
				.andExpect(model().attribute("client", hasProperty("salaryPerYear", is(10000.0))))
				.andExpect(model().attribute("client", hasProperty("age", is(23))))
				.andExpect(model().attribute("client", hasProperty("job", is("WORKER"))))
				.andExpect(model().attribute("client", hasProperty("lastEmployDate", is(lEmpDate))))
				.andExpect(view().name("clients/createOrUpdateClientForm"));

	}

	@WithMockUser(username = "director1", authorities = { "director" })
	@Test
	void testProcessUpdateFormSuccess() throws Exception {
		mockMvc.perform((post("/clients/{clientId}/edit", TEST_CLIENT_ID).with(csrf()).param("firstName", "Client")
				.param("lastName", "Updated").param("address", "Gordal").param("birthDate", "1998/11/27")
				.param("city", "Sevilla").param("maritalStatus", "single but whole").param("salaryPerYear", "300000")
				.param("age", "21").param("job", "student").param("lastEmployDate", "2010/01/22"))
						.param("user.username", "client1").param("user.password", "client1").with(csrf()))
				.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/clients/{clientId}"));
	}
	
	@WithMockUser(username = "director1", authorities = { "director" })
	@Test
	void testUpdateFormClientHasErrors() throws Exception {
		mockMvc.perform(post("/clients/{clientId}/edit", TEST_CLIENT_ID).with(csrf()).param("firstName", "Client")
				.param("lastName", "Updated").param("address", "Gordal")
				.param("city", "Sevilla").param("maritalStatus", "married").param("salaryPerYear", "300000.")
				.param("lastEmployDate", "2010-01-22")).andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("client"))
				.andExpect(model().attributeHasFieldErrors("client", "age"))
				.andExpect(model().attributeHasFieldErrors("client", "job"))
				.andExpect(model().attributeHasFieldErrors("client", "birthDate"))
				.andExpect(view().name("clients/createOrUpdateClientForm"));
	}
	
	//Datos personales como cliente
	
	@WithMockUser(username = "client1", authorities = { "client" })
	@Test
	void testShowPersonalDataByName() throws Exception {
		mockMvc.perform(get("/personalData/{name}", "client1")).andExpect(model().attributeExists("client"))
				.andExpect(model().attribute("client", hasProperty("firstName", is("George"))))
				.andExpect(model().attribute("client", hasProperty("lastName", is("Franklin"))))
				.andExpect(model().attribute("client", hasProperty("address", is("110 W. Liberty St."))))
				.andExpect(model().attribute("client", hasProperty("city", is("Madison"))))
				.andExpect(view().name("clients/clientsDetails")).andExpect(status().is2xxSuccessful());
	}
	
	@WithMockUser(username = "client1", authorities = { "client" })
	@Test
	void initUpdateFormPersonalDataClient() throws Exception {
		final LocalDate bithday = LocalDate.of(1998, 04, 04);
		final LocalDate lEmpDate = LocalDate.of(2018, 06, 06);
		User user = new User();

		user.setUsername("client1");
		user.setPassword("client1");
		user.setEnabled(true);

		mockMvc.perform(get("/personalData/{clientId}/edit", TEST_CLIENT_ID))
				.andExpect(model().attributeExists("client"))
				.andExpect(model().attribute("client", hasProperty("firstName", is("George"))))
				.andExpect(model().attribute("client", hasProperty("lastName", is("Franklin"))))
				.andExpect(model().attribute("client", hasProperty("address", is("110 W. Liberty St."))))
				.andExpect(model().attribute("client", hasProperty("birthDate", is(bithday))))
				.andExpect(model().attribute("client", hasProperty("city", is("Madison"))))
				.andExpect(model().attribute("client", hasProperty("maritalStatus", is("MARRIED"))))
				.andExpect(model().attribute("client", hasProperty("salaryPerYear", is(10000.0))))
				.andExpect(model().attribute("client", hasProperty("age", is(23))))
				.andExpect(model().attribute("client", hasProperty("job", is("WORKER"))))
				.andExpect(model().attribute("client", hasProperty("lastEmployDate", is(lEmpDate))))
				.andExpect(model().attribute("client", hasProperty("user", is(user))))
				.andExpect(view().name("clients/createOrUpdateClientForm"));
	}

	@WithMockUser(username = "client1", authorities = { "client" })
	@Test
	void testUpdateFormPersonalDataSuccess() throws Exception {
		mockMvc.perform(post("/personalData/{clientId}/edit", TEST_CLIENT_ID)
				.param("id", "1")
				.param("firstName", "Client")
				.param("lastName", "Updated")
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

	@WithMockUser(username = "client1", authorities = { "client" })
	@Test
	void testUpdateFormPersonalDataHasErrors() throws Exception {
		mockMvc.perform(post("/personalData/{clientId}/edit", TEST_CLIENT_ID)
				.param("firstName", "Client")
				.param("lastName", "Updated")
				.param("address", "Gordal")
				.param("city", "Sevilla")
				.param("maritalStatus", "married")
				.param("salaryPerYear", "300000.")
				.param("lastEmployDate", "2010/01/22")
				.param("user.username", "client1")
				.param("user.password", "client1").with(csrf()))
				.andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("client"))
				.andExpect(model().attributeHasFieldErrors("client", "age"))
				.andExpect(model().attributeHasFieldErrors("client", "job"))
				.andExpect(model().attributeHasFieldErrors("client", "birthDate"))
				.andExpect(status().is2xxSuccessful())
				.andExpect(view().name("clients/createOrUpdateClientForm"));
	}
}
