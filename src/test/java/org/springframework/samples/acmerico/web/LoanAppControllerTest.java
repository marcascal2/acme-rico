package org.springframework.samples.acmerico.web;

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
import org.springframework.samples.acmerico.model.LoanApplication;
import org.springframework.samples.acmerico.model.User;
import org.springframework.samples.acmerico.service.BankAccountService;
import org.springframework.samples.acmerico.service.ClientService;
import org.springframework.samples.acmerico.service.LoanAppService;
import org.springframework.samples.acmerico.service.LoanService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(controllers = LoanApplicationController.class, 
				excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, 
				classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class LoanAppControllerTest {

	private static User user = new User();
	private static Client client = new Client();
	private static BankAccount bankAccount = new BankAccount();
	private static Loan loan = new Loan();
	private static LoanApplication loanApp = new LoanApplication();

	@MockBean
	private ClientService clientService;

	@MockBean
	private LoanAppService loanAppService;

	@MockBean
	private BankAccountService accountService;

	@MockBean
	private LoanService loanService;

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
		client.setLoanApps(Arrays.asList());

		bankAccount.setId(1);
		bankAccount.setAccountNumber("ES23 2323 2323 2323 2323");
		bankAccount.setAmount(10000.0);
		bankAccount.setCreatedAt(LocalDateTime.parse("2017-10-30T12:30:00"));
		bankAccount.setAlias("Viajes");
		bankAccount.setClient(client);
		bankAccount.setLoanApps(Arrays.asList());

		loan.setId(1);
		loan.setDescription("This is a Description");
		loan.setMinimum_amount(1000.0);
		loan.setMinimum_income(600.0);
		loan.setNumber_of_deadlines(2);
		loan.setOpening_price(100.0);
		loan.setMonthly_fee(0.02);
		loan.setSingle_loan(false);
		loan.setLoanApplications(Arrays.asList());

		loanApp.setId(1);
		loanApp.setAmount(900.0);
		loanApp.setPurpose("This is a purpose");
		loanApp.setStatus("PENDING");
		loanApp.setAmount_paid(0.0);
		loanApp.setPayedDeadlines(0);
	}

	@WithMockUser(username = "userPrueba", roles = { "client" })
	@Test
	void testListClientLoanApp() throws Exception {
		when(this.clientService.findClientByUserName("userPrueba")).thenReturn(client);
		when(this.loanAppService.findLoanAppsByClient(client.getId())).thenReturn(Arrays.asList(loanApp));

		mockMvc.perform(get("/myloanapps")).andExpect(status().isOk()).andExpect(model().attributeExists("loanApps"))
				.andExpect(model().attributeExists("clientUser")).andExpect(view().name("loanApp/clientLoanApps"))
				.andExpect(status().is2xxSuccessful());
	}

	@WithMockUser(username = "userPrueba", roles = { "director", "worker" })
	@Test
	void testListEmployeeLoanApp() throws Exception {
		when(this.loanAppService.findPendingsLoanApps()).thenReturn(Arrays.asList(loanApp));

		mockMvc.perform(get("/loanapps")).andExpect(status().isOk()).andExpect(model().attributeExists("loanApps"))
				.andExpect(view().name("loanApp/loanAppList")).andExpect(status().is2xxSuccessful());
	}

	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception {
		when(this.loanService.findLoanById(loan.getId())).thenReturn(loan);

		mockMvc.perform(get("/loanapps/{loanId}/new/{bankAccountId}", loan.getId(), bankAccount.getId()))
				.andExpect(status().isOk()).andExpect(model().attributeExists("loan_app"))
				.andExpect(view().name("loanApp/createOrUpdateLoanApp")).andExpect(status().is2xxSuccessful());
	}

	@WithMockUser(username = "userPrueba", roles = { "client" })
	@Test
	void testProcessCreationForm() throws Exception {
		when(this.loanService.findLoanById(loan.getId())).thenReturn(loan);
		when(this.accountService.findBankAccountById(bankAccount.getId())).thenReturn(bankAccount);
		
		mockMvc.perform(post("/loanapps/{loanId}/new/{bankAccountId}", loan.getId(), bankAccount.getId())
				.with(csrf()).param("amount", "100.0").param("amount_paid", "0.0").param("id", "1")
				.param("purpose", "This is a purpose").param("status", "PENDING"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/myloanapps"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testAcceptLoanApplication() throws Exception {
		mockMvc.perform(get("/loanapps/{loanappsId}/accept", loanApp.getId())).andExpect(status().isFound())
				.andExpect(view().name("redirect:/loanapps")).andExpect(status().is3xxRedirection());

	}

	@WithMockUser(value = "spring")
	@Test
	void testRefuseLoanApplication() throws Exception {
		mockMvc.perform(get("/loanapps/{loanappsId}/refuse", loanApp.getId())).andExpect(status().isFound())
				.andExpect(view().name("redirect:/loanapps")).andExpect(status().is3xxRedirection());
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testCollectAcceptedLoans() throws Exception {
		mockMvc.perform(get("/loanapps/collect")).andExpect(status().isFound())
				.andExpect(view().name("redirect:/loanapps")).andExpect(status().is3xxRedirection());
	}

}
