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
public class LoanControllerIntegrationTests {
	
	private static final Integer TEST_BANK_ACCOUNT_ID = 1;
	private static final Integer TEST_LOAN_ID = 1;

	@Autowired
	private MockMvc mockMvc;

	@WithMockUser(username = "director1", authorities = { "director" })
	@Test
	void testListLoan() throws Exception {
		mockMvc.perform(get("/grantedLoans")).andExpect(status().isOk()).andExpect(model().attributeExists("loans"))
				.andExpect(view().name("loans/loanList")).andExpect(status().is2xxSuccessful());
	}

	@WithMockUser(username = "client1", authorities = { "client" })
	@Test
	void testListLoanForBankAccount() throws Exception {
		mockMvc.perform(get("/loans/{bankAccountId}", TEST_BANK_ACCOUNT_ID)).andExpect(status().isOk())
				.andExpect(model().attributeExists("loans")).andExpect(model().attributeExists("bankAccountId"))
				.andExpect(view().name("loans/personalicedLoanList")).andExpect(status().is2xxSuccessful());
	}

	@WithMockUser(username = "director1", authorities = { "director" })
	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/grantedLoans/new")).andExpect(status().isOk()).andExpect(model().attributeExists("loan"))
				.andExpect(view().name("loans/loanInfo")).andExpect(status().is2xxSuccessful());
	}

	@WithMockUser(username = "director1", authorities = { "director" })
	@Test
	void testProcessCreationFormSuccessful() throws Exception {
		mockMvc.perform(post("/grantedLoans/new").with(csrf()).param("description", "This is a description")
				.param("minimum_amount", "1000.0").param("minimum_income", "600.0").param("number_of_deadlines", "2")
				.param("opening_price", "1.0").param("monthly_fee", "0.02").param("single_loan", "true"))
				.andExpect(status().isFound()).andExpect(view().name("redirect:/grantedLoans"))
				.andExpect(status().is3xxRedirection());
	}

	@WithMockUser(username = "director1", authorities = { "director" })
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/grantedLoans/new").with(csrf()).param("description", "This is a description")
				.param("minimum_amount", "1000.0").param("minimum_income", "200.0").param("number_of_deadlines", "2")
				.param("opening_price", "1.0").param("monthly_fee", "0.02").param("single_loan", "true"))
				.andExpect(status().isOk()).andExpect(model().attributeHasErrors("loan"))
				.andExpect(model().attributeHasFieldErrors("loan", "minimum_income"))
				.andExpect(view().name("loans/loanInfo")).andExpect(status().is2xxSuccessful());
	}

	@WithMockUser(username = "director1", authorities = { "director" })
	@Test
	void testShowDirectorLoan() throws Exception {
		mockMvc.perform(get("/grantedLoans/{loanId}", TEST_LOAN_ID)).andExpect(status().isOk())
				.andExpect(model().attributeExists("loan")).andExpect(view().name("loans/loanInfo"))
				.andExpect(status().is2xxSuccessful());
	}

	@WithMockUser(username = "client1", authorities = { "client" })
	@Test
	void testShowClientLoan() throws Exception {
		mockMvc.perform(get("/loans/{loanId}/{bankAccountId}", TEST_LOAN_ID, TEST_BANK_ACCOUNT_ID))
				.andExpect(status().isOk()).andExpect(model().attributeExists("loan"))
				.andExpect(model().attributeExists("bankAccountId"))
				.andExpect(model().attributeExists("clientSingleLoan")).andExpect(view().name("loans/loanInfo"))
				.andExpect(status().is2xxSuccessful());
	}

}
