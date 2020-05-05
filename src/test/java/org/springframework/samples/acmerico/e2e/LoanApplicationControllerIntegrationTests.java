package org.springframework.samples.acmerico.e2e;

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
public class LoanApplicationControllerIntegrationTests {

	private static final Integer TEST_LOAN_APPLICATION_ID = 1;

	private static final Integer TEST_BANK_ACCOUNT_ID = 1;

	@Autowired
	private MockMvc mockMvc;
	
	@WithMockUser(username = "client1", authorities = { "client" })
	@Test
	void testListClientLoanApp() throws Exception {
		mockMvc.perform(get("/myloanapps")).andExpect(status().isOk()).andExpect(model().attributeExists("loanApps"))
				.andExpect(model().attributeExists("clientUser")).andExpect(view().name("loanApp/clientLoanApps"))
				.andExpect(status().is2xxSuccessful());
	}

	@WithMockUser(username = "client1", authorities = { "client" })
	@Test
	void testListEmployeeLoanApp() throws Exception {
		mockMvc.perform(get("/loanapps")).andExpect(status().isOk()).andExpect(model().attributeExists("loanApps"))
				.andExpect(view().name("loanApp/loanAppList")).andExpect(status().is2xxSuccessful());
	}

	@WithMockUser(username = "client1", authorities = { "client" })
	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/loanapps/{loanId}/new/{bankAccountId}", TEST_LOAN_APPLICATION_ID, TEST_BANK_ACCOUNT_ID))
				.andExpect(status().isOk()).andExpect(model().attributeExists("loan_app"))
				.andExpect(view().name("loanApp/createOrUpdateLoanApp")).andExpect(status().is2xxSuccessful());
	}

	@WithMockUser(username = "client1", authorities = { "client" })
	@Test
	void testProcessCreationForm() throws Exception {
		mockMvc.perform(post("/loanapps/{loanId}/new/{bankAccountId}", TEST_LOAN_APPLICATION_ID, TEST_BANK_ACCOUNT_ID)
				.with(csrf()).param("amount", "2000.0").param("amount_paid", "0.0")
				.param("purpose", "This is a purpose").param("status", "PENDING"))
				.andExpect(status().is2xxSuccessful());
	}

	@WithMockUser(username = "worker1", authorities = { "worker" })
	@Test
	void testAcceptLoanApplication() throws Exception {
		mockMvc.perform(get("/loanapps/{loanappsId}/accept", TEST_LOAN_APPLICATION_ID)).andExpect(status().isFound())
				.andExpect(view().name("redirect:/loanapps")).andExpect(status().is3xxRedirection());

	}

	@WithMockUser(username = "worker1", authorities = { "worker" })
	@Test
	void testRefuseLoanApplication() throws Exception {
		mockMvc.perform(get("/loanapps/{loanappsId}/refuse", TEST_LOAN_APPLICATION_ID)).andExpect(status().isFound())
				.andExpect(view().name("redirect:/loanapps")).andExpect(status().is3xxRedirection());
	}
	
	@WithMockUser(username = "director1", authorities = { "director" })
	@Test
	void testCollectAcceptedLoans() throws Exception {
		mockMvc.perform(get("/loanapps/collect")).andExpect(status().isFound())
				.andExpect(view().name("redirect:/grantedLoans")).andExpect(status().is3xxRedirection());
	}
}
