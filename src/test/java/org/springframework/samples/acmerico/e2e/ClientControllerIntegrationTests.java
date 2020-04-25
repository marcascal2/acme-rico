package org.springframework.samples.acmerico.e2e;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.samples.acmerico.service.ClientService;
import org.springframework.samples.acmerico.service.UserService;
import org.springframework.samples.acmerico.web.ClientController;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.ModelMap;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ClientControllerIntegrationTests {

	private static final Integer TEST_CLIENT_ID = 1;

	@Autowired
	private ClientController clientController;

	@MockBean
	private ClientService clientService;

	@MockBean
	private UserService UserService;

	@Autowired
	private MockMvc mockMvc;

	@Test
	void testInitCreationForm() throws Exception {
		ModelMap model = new ModelMap();
		String view = clientController.initCreationForm(model);
		assertEquals(view, "clients/createOrUpdateClientForm");
		assertNotNull(model.get("client"));
	}

	@WithMockUser(username = "director1", authorities = { "director" })
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform((post("/clients/{clientId}/edit", TEST_CLIENT_ID).with(csrf())
				.param("firstName", "Client")
				.param("lastName", "Updated")
				.param("address", "Gordal")
				.param("birthDate", "1998/11/27")
				.param("city", "Sevilla")
				.param("maritalStatus", "single but whole")
				.param("salaryPerYear", "300000.")
				.param("age", "21")
				.param("job", "student")
				.param("lastEmployDate", "2010/01/22"))
				.with(csrf()))
				.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/clients/{clientId}"));
	}

}
