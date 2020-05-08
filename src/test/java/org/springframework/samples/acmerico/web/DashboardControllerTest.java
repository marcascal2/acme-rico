package org.springframework.samples.acmerico.web;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
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
import org.springframework.samples.acmerico.service.DashboardService;
import org.springframework.samples.acmerico.service.LoanAppService;
import org.springframework.samples.acmerico.service.LoanService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.ModelMap;


@WebMvcTest(controllers = DashboardController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)

public class DashboardControllerTest {

	private static final Integer TEST_CLIENT_ID = 1;

	private static final String TEST_CLIENT_USER = "client1";

	private static final String TEST_CLIENT_LASTNAME = "Ruiz";

	private static final Integer TEST_BANK_ACCOUNT_ID = 1;

	private static final String TEST_BANK_ACCOUNT_NUMBER = "ES23 0025 0148 1259 1424";

	@MockBean
	private DashboardService dashboardService;

	@MockBean
	private ClientService clientService;

	@MockBean
	private BankAccountService bankAccountService;

	@MockBean
	private LoanAppService loanAppService;

	@MockBean
	private LoanService loanService;

	@Autowired
	private MockMvc mockMvc;

	private ModelMap modelMap;
	private Client javier;
	private User user;
	private BankAccount account;
	private BankAccount account2;
	private Loan loan;
	private LoanApplication loanApp;
	private LoanApplication loanApp2;
	private LoanApplication loanApp3;

	@BeforeEach
	void setUp() {

		modelMap = new ModelMap();
		MockitoAnnotations.initMocks(this);

		final LocalDate bithday = LocalDate.of(1998, 11, 27);
		final LocalDate lEmpDate = LocalDate.of(2010, 01, 22);
		final LocalDateTime creationDate = LocalDateTime.of(2019, 11, 23, 12, 12, 12);
		Collection<BankAccount> accounts = new ArrayList<BankAccount>();

		user = new User();

		user.setUsername(TEST_CLIENT_USER);
		user.setPassword("client1");
		user.setEnabled(true);

		javier = new Client();

		javier.setId(TEST_CLIENT_ID);
		javier.setFirstName("Javier");
		javier.setLastName(TEST_CLIENT_LASTNAME);
		javier.setAddress("Gordal");
		javier.setBirthDate(bithday);
		javier.setCity("Sevilla");
		javier.setMaritalStatus("married");
		javier.setSalaryPerYear(300000.);
		javier.setAge(21);
		javier.setJob("student");
		javier.setLastEmployDate(lEmpDate);
		javier.setUser(user);

		loan = new Loan();
		loanApp = new LoanApplication();
		loanApp2 = new LoanApplication();
		loanApp3 = new LoanApplication();

		loanApp.setId(1);
		loanApp.setAmount(900.0);
		loanApp.setPurpose("This is a purpose");
		loanApp.setStatus("PENDING");
		loanApp.setAmount_paid(0.0);
		
		loanApp2.setId(2);
		loanApp2.setAmount(900.0);
		loanApp2.setPurpose("This is a purpose");
		loanApp2.setStatus("ACCEPTED");
		loanApp2.setAmount_paid(0.0);
		
		loanApp3.setId(3);
		loanApp3.setAmount(900.0);
		loanApp3.setPurpose("This is a purpose");
		loanApp3.setStatus("REJECTED");
		loanApp3.setAmount_paid(0.0);

		loan.setId(1);
		loan.setDescription("This is a Description");
		loan.setMinimum_amount(1000.0);
		loan.setMinimum_income(600.0);
		loan.setNumber_of_deadlines(2);
		loan.setOpening_price(100.0);
		loan.setMonthly_fee(0.02);
		loan.setSingle_loan(false);
		loan.setLoanApplications(Arrays.asList(loanApp));

		account = new BankAccount();
		account2 = new BankAccount();

		account.setAccountNumber(TEST_BANK_ACCOUNT_NUMBER);
		account.setAlias("Cuenta personal");
		account.setAmount(10000.);
		account.setCreatedAt(creationDate);
		account.setLoanApps(Arrays.asList(loanApp));
		account.setClient(javier);

		account2.setAccountNumber("ES23 0025 2222 1259 1424");
		account2.setAlias("Segunda Cuenta personal");
		account2.setAmount(5000.);
		account2.setCreatedAt(creationDate);
		account2.setClient(javier);

		accounts.add(account);
		accounts.add(account2);

		javier.setBankAccounts(accounts);
		javier.setLoanApps(Arrays.asList(loanApp));
			
		when(this.clientService.findClientById(TEST_CLIENT_ID)).thenReturn(javier);
	}

	@WithMockUser(username = "client1",roles = {"client"})
    @Test
    void testShowClientCards() throws Exception{
		mockMvc.perform(get("/dashboard")).andExpect(status().isOk())
			.andExpect(model().size(9))
			.andExpect(status().is2xxSuccessful())
			.andExpect(view().name("dashboard/clientMoneyInfo"));
		
	}

	@WithMockUser(value = "spring")
	@Test
	void testShowClientsStatics() throws Exception {
		mockMvc.perform(get("/dashboard/{clientId}/statics", TEST_CLIENT_ID, modelMap)).andExpect(status().isOk())
				.andExpect(model().attributeExists("countAccounts")).andExpect(model().attributeExists("clientLoans"))
				.andExpect(model().attributeExists("borrowedAmount")).andExpect(model().attributeExists("totalAmount"))
				.andExpect(model().hasNoErrors()).andExpect(status().is2xxSuccessful())
				.andExpect(view().name("dashboard/clientStatics"));
	}

}