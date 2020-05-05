package org.springframework.samples.acmerico.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.acmerico.api.service.DropboxService;
import org.springframework.samples.acmerico.configuration.SecurityConfiguration;
import org.springframework.samples.acmerico.model.User;
import org.springframework.samples.acmerico.service.AuthoritiesService;
import org.springframework.samples.acmerico.service.ClientService;
import org.springframework.samples.acmerico.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;

@WebMvcTest(controllers = UserController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class UserControllerTest {

	@MockBean
	private UserService userService;

	@MockBean
	private AuthoritiesService authoritiesService;

	@MockBean
	private ClientService clientService;

	@MockBean
	private DropboxService dropboxService;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BindingResult bindingResult;

	private User user = new User();

	@BeforeEach
	void setUp() {
		user.setUsername("user1");
		user.setPassword("user1");
		user.setEnabled(true);
	}

	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/users/new")).andExpect(status().isOk()).andExpect(model().attributeExists("client"))
				.andExpect(view().name("users/createClientForm")).andExpect(status().is2xxSuccessful());
	}
}