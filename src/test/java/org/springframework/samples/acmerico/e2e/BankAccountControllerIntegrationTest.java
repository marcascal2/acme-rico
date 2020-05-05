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
public class BankAccountControllerIntegrationTest {

	private static final Integer TEST_CLIENT_ID = 1;
	private static final String TEST_BANK_ACCOUNT_NUMBER = "ES23 0025 0148 1259 1424";
	private static final Integer TEST_BANK_ACCOUNT_ID = 1;

	@Autowired
	private MockMvc mockMvc;

	@WithMockUser(username = "client1", authorities = { "client" })
	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/accounts/{clientId}/new", TEST_CLIENT_ID)).andExpect(status().isOk())
				.andExpect(model().attributeExists("bankAccount")).andExpect(model().attributeDoesNotExist("alias"))
				.andExpect(status().is2xxSuccessful()).andExpect(view().name("accounts/createAccountForm"));
	}

	@WithMockUser(username = "client1", authorities = { "client" })
	@Test
	void testProcessCreationFormWithErrors() throws Exception {
		mockMvc.perform(
				post("/accounts/{clientId}/new", TEST_CLIENT_ID).param("accountNumber", TEST_BANK_ACCOUNT_NUMBER)
						.param("amount", "10000.").param("alias", "Cuenta Personal"))
				.andExpect(status().is4xxClientError());
	}

	@WithMockUser(username = "client1", authorities = { "client" })
	@Test
	void testDeleteSuccess() throws Exception {
		mockMvc.perform(get("/accounts/{accountId}/delete", TEST_BANK_ACCOUNT_ID))
				.andExpect(view().name("redirect:/accounts/")).andExpect(status().is3xxRedirection());
	}

	@WithMockUser(username = "client1", authorities = { "client" })
	@Test
	void testShowClientById() throws Exception {
		mockMvc.perform(get("/accounts/{accountId}", TEST_BANK_ACCOUNT_ID))
				.andExpect(model().attributeExists("bankAccount"))
				.andExpect(model().attribute("bankAccount", hasProperty("accountNumber", is(TEST_BANK_ACCOUNT_NUMBER))))
				.andExpect(model().attribute("bankAccount", hasProperty("amount", is(2567.34))))
				.andExpect(status().is2xxSuccessful()).andExpect(view().name("accounts/showAccountInfo"));
	}

	@WithMockUser(username = "client1", authorities = { "client" })
	@Test
	void testInitDeposit() throws Exception {
		mockMvc.perform(get("/accounts/{accountId}/depositMoney", TEST_BANK_ACCOUNT_ID))
				.andExpect(model().attributeExists("bankAccount"))
				.andExpect(model().attribute("bankAccount", hasProperty("accountNumber", is(TEST_BANK_ACCOUNT_NUMBER))))
				.andExpect(model().attribute("bankAccount", hasProperty("amount", is(2567.34))))
				.andExpect(status().is2xxSuccessful()).andExpect(view().name("accounts/depositMoney"));
	}

	@WithMockUser(username = "client1", authorities = { "client" })
	@Test
	void testMakeDepositSuccess() throws Exception {
		mockMvc.perform(post("/accounts/{accountId}/depositMoney", TEST_BANK_ACCOUNT_ID).with(csrf())
				.param("accountNumber", "ES23 0025 2222 1259 1424").param("alias", "Segunda Cuenta personal")
				.param("amount", "5000.0")).andExpect(view().name("redirect:/accounts/"));
	}

}
