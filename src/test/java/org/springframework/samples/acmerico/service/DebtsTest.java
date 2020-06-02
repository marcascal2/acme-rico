package org.springframework.samples.acmerico.service;

import static org.assertj.core.api.Assertions.assertThat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.acmerico.model.BankAccount;
import org.springframework.samples.acmerico.model.Client;
import org.springframework.samples.acmerico.model.Debt;
import org.springframework.samples.acmerico.model.Loan;
import org.springframework.samples.acmerico.model.LoanApplication;
import org.springframework.samples.acmerico.model.User;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class DebtsTest {

	@Autowired
	private DebtService debtService;
	
	private static Debt debt = new Debt();
	private static User user = new User();
	private static Client client = new Client();
	private static BankAccount bankAccount = new BankAccount();
	private static LoanApplication loanApplication = new LoanApplication();
	private static Loan loan = new Loan();
	
	@BeforeAll
	private static void setUp() {
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
		client.setSalaryPerYear(2000.0);
		client.setAge(21);
		client.setJob("DP2 Developement Student");
		client.setLastEmployDate(LocalDate.parse("2019-04-15"));
		client.setUser(user);
		client.setBankAccounts(Arrays.asList(bankAccount));
		client.setLoanApps(Arrays.asList(loanApplication));
		client.setDebts(Arrays.asList(debt));
		
		bankAccount.setId(1);
		bankAccount.setAccountNumber("ES23 2323 2323 2323 2323");
		bankAccount.setAmount(10000.0);
		bankAccount.setCreatedAt(LocalDateTime.parse("2017-10-30T12:30:00"));
		bankAccount.setAlias("Viajes");
		bankAccount.setClient(client);
		bankAccount.setLoanApps(Arrays.asList(loanApplication));
		
		loanApplication.setId(1);
		loanApplication.setAmount(2000.0);
		loanApplication.setPurpose("This is a purpose");
		loanApplication.setStatus("PENDING");
		loanApplication.setAmount_paid(100.0);
		loanApplication.setPayedDeadlines(0);
		loanApplication.setBankAccount(bankAccount);
		loanApplication.setLoan(loan);
		loanApplication.setClient(client);
		
		loan.setId(1);
		loan.setDescription("This is a Description");
		loan.setMinimum_amount(1000.0);
		loan.setMinimum_income(1000.0);
		loan.setNumber_of_deadlines(2);
		loan.setOpening_price(100.0);
		loan.setMonthly_fee(0.02);
		loan.setSingle_loan(true);
		loan.setLoanApplications(Arrays.asList(loanApplication));
	}
	
	@BeforeEach
	private void reset() {
		debt.setId(1);
		debt.setAmount(1000.0);
		debt.setRefreshDate("11/2019");
		debt.setClient(client);
		debt.setLoanApplication(loanApplication);
		this.debtService.save(debt);
	}
	
	@Test
	public void testGetNonPayedDebts() {
		Collection<Debt> allDebts = (Collection<Debt>) this.debtService.getNonPayedDebts();
		assertThat(allDebts.size()).isEqualTo(1);
	}
	
	@Test
	public void testGetClientDebt() {
		Debt debtClient = (Debt) this.debtService.getClientDebt(client);
		assertThat(debtClient.getId()).isEqualTo(debt.getId());
	}
	
}
