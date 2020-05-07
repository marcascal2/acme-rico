package org.springframework.samples.acmerico.web;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.acmerico.configuration.SecurityConfiguration;
import org.springframework.samples.acmerico.model.BankAccount;
import org.springframework.samples.acmerico.model.Client;
import org.springframework.samples.acmerico.model.Loan;
import org.springframework.samples.acmerico.model.User;
import org.springframework.samples.acmerico.service.BankAccountService;
import org.springframework.samples.acmerico.service.LoanService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = LoanController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class LoanControllerTest {

	private static User user = new User();
	private static Client client = new Client();
	private static BankAccount bankAccount = new BankAccount();
	private static Loan loan = new Loan();

	@MockBean
	private LoanService loanService;

	@MockBean
	private BankAccountService bankAccountService;

	@Autowired
	private MockMvc mockMvc;

	@BeforeAll
	static void setUp() {
		user.setUsername("userPrueba");
		user.setPassword("userPrueba");
		user.setEnabled(true);

		client.setId(1);
		client.setFirstName("Germán");
		client.setLastName("Márquez Trujillo");
		client.setAddress("C/ Marques de Aracena, 37");
		client.setBirthDate(LocalDate.parse("1998-04-15"));
		client.setCity("Sevilla");
		client.setMaritalStatus("Single");
		client.setSalaryPerYear(20000.0);
		client.setAge(21);
		client.setJob("DP2 Developement Student");
		client.setLastEmployDate(LocalDate.parse("2019-04-15"));
		client.setUser(user);
		client.setBankAccounts(Arrays.asList(bankAccount));

		bankAccount.setId(1);
		bankAccount.setAccountNumber("ES23 2323 2323 2323 2323");
		bankAccount.setAmount(10000.0);
		bankAccount.setCreatedAt(LocalDateTime.parse("2017-10-30T12:30:00"));
		bankAccount.setAlias("Viajes");
		bankAccount.setClient(client);

		loan.setId(1);
		loan.setDescription("This is a Description");
		loan.setMinimum_amount(1000.0);
		loan.setMinimum_income(600.0);
		loan.setNumber_of_deadlines(2);
		loan.setOpening_price(100.0);
		loan.setMonthly_fee(0.02);
		loan.setSingle_loan(false);
	}

	@WithMockUser(value = "spring")
	@Test
	void testListLoan() throws Exception {
		when(this.loanService.findAllLoans()).thenReturn(Arrays.asList(loan));

		mockMvc.perform(get("/grantedLoans")).andExpect(status().isOk()).andExpect(model().attributeExists("loans"))
				.andExpect(view().name("loans/loanList")).andExpect(status().is2xxSuccessful());
	}

	@WithMockUser(value = "spring")
	@Test
	void testListLoanForBankAccount() throws Exception {
		when(this.loanService.findAllLoans()).thenReturn(Arrays.asList(loan));
		when(this.bankAccountService.findBankAccountById(bankAccount.getId())).thenReturn(bankAccount);

		mockMvc.perform(get("/loans/{bankAccountId}", bankAccount.getId())).andExpect(status().isOk())
				.andExpect(model().attributeExists("loans")).andExpect(model().attributeExists("bankAccountId"))
				.andExpect(view().name("loans/personalicedLoanList")).andExpect(status().is2xxSuccessful());
	}

	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/grantedLoans/new")).andExpect(status().isOk()).andExpect(model().attributeExists("loan"))
				.andExpect(view().name("loans/loanInfo")).andExpect(status().is2xxSuccessful());
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormSuccessful() throws Exception {
		mockMvc.perform(post("/grantedLoans/new").with(csrf()).param("description", "This is a description")
				.param("minimum_amount", "1000.0").param("minimum_income", "600.0").param("number_of_deadlines", "2")
				.param("opening_price", "1.0").param("monthly_fee", "0.02").param("single_loan", "true"))
				.andExpect(status().isFound()).andExpect(view().name("redirect:/grantedLoans"))
				.andExpect(status().is3xxRedirection());
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/grantedLoans/new").with(csrf()).param("description", "This is a description")
				.param("minimum_amount", "1000.0").param("minimum_income", "200.0").param("number_of_deadlines", "2")
				.param("opening_price", "1.0").param("monthly_fee", "0.02").param("single_loan", "true"))
				.andExpect(status().isOk()).andExpect(model().attributeHasErrors("loan"))
				.andExpect(model().attributeHasFieldErrors("loan", "minimum_income"))
				.andExpect(view().name("loans/loanInfo")).andExpect(status().is2xxSuccessful());
	}

	@WithMockUser(value = "spring")
	@Test
	void testShowDirectorLoan() throws Exception {
		when(this.loanService.findLoanById(loan.getId())).thenReturn(loan);
		when(this.loanService.acceptedLoanApps(loan)).thenReturn(Arrays.asList());
		
		mockMvc.perform(get("/grantedLoans/{loanId}", loan.getId())).andExpect(status().isOk())
				.andExpect(model().attributeExists("acceptedLoanApps")).andExpect(model().attributeExists("loan"))
				.andExpect(view().name("loans/loanInfo")).andExpect(status().is2xxSuccessful());
	}

	@WithMockUser(value = "spring")
	@Test
	void testShowClientLoan() throws Exception {
		when(this.loanService.findLoanById(loan.getId())).thenReturn(loan);
		when(this.loanService.checkSingleLoan(bankAccount.getId())).thenReturn(true);

		mockMvc.perform(get("/loans/{loanId}/{bankAccountId}", loan.getId(), bankAccount.getId()))
				.andExpect(status().isOk()).andExpect(model().attributeExists("loan"))
				.andExpect(model().attributeExists("bankAccountId"))
				.andExpect(view().name("loans/loanInfo"))
				.andExpect(status().is2xxSuccessful());
	}

}
