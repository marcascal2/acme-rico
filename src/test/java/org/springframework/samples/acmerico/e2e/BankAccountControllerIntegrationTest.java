package org.springframework.samples.acmerico.e2e;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.samples.acmerico.service.BankAccountService;
import org.springframework.samples.acmerico.service.ClientService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.hamcrest.Matchers.hasProperty;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
//@TestPropertySource(
//  locations = "classpath:application-mysql.properties")
public class BankAccountControllerIntegrationTest {

	private static final Integer TEST_CLIENT_ID = 1;
	private static final String TEST_BANK_ACCOUNT_NUMBER = "ES23 0025 0148 1259 1424";
	private static final Integer TEST_BANK_ACCOUNT_ID = 1;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ClientService clientService;

	@MockBean
	private BankAccountService bankAccountService;

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
		// ModelAndView modelAndView = Mockito.mock(ModelAndView.class);

		mockMvc.perform(
				post("/accounts/{clientId}/new", TEST_CLIENT_ID).param("accountNumber", TEST_BANK_ACCOUNT_NUMBER)
						.param("amount", "10000.").param("alias", "Cuenta Personal"))
				.andExpect(status().is4xxClientError());

	}

//	@WithMockUser(username = "client1", authorities = { "client" })
//	@Test
//
//	void testShowClientById() throws Exception {
//
//		final LocalDateTime creationDate = LocalDateTime.of(2019, 11, 23, 12, 12, 12);
//
//		mockMvc.perform(get("/accounts/{accountId}", TEST_BANK_ACCOUNT_ID))
//				.andExpect(model().attributeExists("bankAccount"))
//				.andExpect(model().attribute("bankAccount", hasProperty("accountNumber", is(TEST_BANK_ACCOUNT_NUMBER))))
//				.andExpect(model().attribute("bankAccount", hasProperty("amount", is(10000.0))))
//				.andExpect(model().attribute("bankAccount", hasProperty("createdAt", is(creationDate))))
//				.andExpect(status().is2xxSuccessful()).andExpect(view().name("accounts/showAccountInfo"));
//
//	}

	// Test delete
	@WithMockUser(username = "client1", authorities = { "client" })
	@Test
	void testDeleteSuccess() throws Exception {

		mockMvc.perform(get("/accounts/{accountId}/delete", TEST_BANK_ACCOUNT_ID))
				.andExpect(view().name("redirect:/accounts/")).andExpect(status().is3xxRedirection());
	}

//	@WithMockUser(username = "client1", authorities = { "client" })
//	@Test
//	void testInitDeposit() throws Exception {
//
//	final LocalDateTime creationDate = LocalDateTime.of(2019, 11, 23, 12, 12, 12);
//
//	 	mockMvc.perform(get("/accounts/{accountId}/depositMoney", TEST_BANK_ACCOUNT_ID))
//	                .andExpect(model().attributeExists("bankAccount"))
//	                .andExpect(model().attribute("bankAccount", hasProperty("accountNumber", is(TEST_BANK_ACCOUNT_NUMBER))))
//	                .andExpect(model().attribute("bankAccount", hasProperty("amount", is(10000.0))))
//	                .andExpect(model().attribute("bankAccount", hasProperty("createdAt", is(creationDate))))
//	                .andExpect(status().is2xxSuccessful()).andExpect(view().name("accounts/depositMoney"));
//
//	}

//	@WithMockUser(username = "client1", authorities = { "client" })
//	@Test
//
//	void testMakeDepositSuccess() throws Exception {
//		mockMvc.perform(post("/accounts/{accountId}/depositMoney", TEST_BANK_ACCOUNT_ID).with(csrf())
//				.param("accountNumber", "ES23 0025 2222 1259 1424").param("alias", "Segunda Cuenta personal")
//				.param("amount", "5000.0").param("createdAt", "2010/01/22"))
//				.andExpect(view().name("redirect:/accounts/"));
//
//	}

}
