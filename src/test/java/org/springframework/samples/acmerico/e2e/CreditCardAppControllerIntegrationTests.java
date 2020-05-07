package org.springframework.samples.acmerico.e2e;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
public class CreditCardAppControllerIntegrationTests {

	private static final int TEST_CREDIT_CARD_APP_ID = 1;
	private static final Integer TEST_BANK_ACCOUNT_ID = 1;

	@Autowired
	private MockMvc mockMvc;

	@WithMockUser(username = "worker1", authorities = { "worker" })
	@Test
	void testListSuccess() throws Exception {
		mockMvc.perform(get("/creditcardapps")).andExpect(status().isOk()).andExpect(status().is2xxSuccessful())
				.andExpect(model().attributeExists("credit_cards_app"))
				.andExpect(view().name("creditCardApps/creditCardAppList"));
	}

	@WithMockUser(username = "worker1", authorities = { "worker" })
	@Test
	void testShowCreditCardApp() throws Exception {
		mockMvc.perform(get("/creditcardapps/{creditcardappsId}", TEST_CREDIT_CARD_APP_ID)).andExpect(status().isOk())
				.andExpect(model().attributeExists("credit_card_app"))
				.andExpect(view().name("creditCardApps/creditCardAppDetails")).andExpect(status().is2xxSuccessful());
	}

	@WithMockUser(username = "worker1", authorities = { "worker" })
	@Test
	void testAcceptCreditCardAppplication() throws Exception {
		mockMvc.perform(get("/creditcardapps/{creditcardappsId}/accept", TEST_CREDIT_CARD_APP_ID))
				.andExpect(status().isFound()).andExpect(view().name("redirect:/creditcardapps"))
				.andExpect(status().is3xxRedirection());
	}

	@WithMockUser(username = "worker1", authorities = { "worker" })
	@Test
	void testRefuseCreditCardApplication() throws Exception {
		mockMvc.perform(get("/creditcardapps/{creditcardappsId}/refuse", TEST_CREDIT_CARD_APP_ID))
				.andExpect(status().isFound()).andExpect(view().name("redirect:/creditcardapps"))
				.andExpect(status().is3xxRedirection());
	}

	@WithMockUser(username = "client1", authorities = { "client" })
	@Test
	void testShowClientCreditCardApp() throws Exception {
		mockMvc.perform(get("/mycreditcardapps")).andExpect(status().isOk())
				.andExpect(model().attributeExists("cardApps")).andExpect(model().attributeExists("clientUser"))
				.andExpect(view().name("creditCardApps/clientCreditCardApps")).andExpect(status().is2xxSuccessful());
	}

	@WithMockUser(username = "client1", authorities = { "client" })
	@Test
	void testRequestNewCreditCardHasErrors() throws Exception {
		mockMvc.perform(get("/creditcardapps/{bankAccountId}/new", 0))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/oups"));

	}

	@WithMockUser(username = "client1", authorities = { "client" })
	@Test
	void testRequestNewCreditCardSuccess() throws Exception {
		mockMvc.perform(get("/creditcardapps/{bankAccountId}/new", TEST_BANK_ACCOUNT_ID))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/creditcardapps/created"));
	}

	@WithMockUser(username = "client1", authorities = { "client" })
	@Test
	void testRequestCreated() throws Exception {
		mockMvc.perform(get("/creditcardapps/created")).andExpect(status().is2xxSuccessful())
				.andExpect(view().name("creditCardApps/successfullyCreated"));
	}
}
