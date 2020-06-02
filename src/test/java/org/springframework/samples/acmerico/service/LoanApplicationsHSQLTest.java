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
import org.springframework.samples.acmerico.model.Loan;
import org.springframework.samples.acmerico.model.LoanApplication;
import org.springframework.samples.acmerico.model.User;
import org.springframework.samples.acmerico.projections.ClientLoanApp;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class LoanApplicationsHSQLTest {

	@Autowired
	private LoanAppService loanApplicationService;

	private static User user = new User();
	private static Client client = new Client();
	private static BankAccount bankAccount1 = new BankAccount();
	private static BankAccount bankAccount2 = new BankAccount();
	private static Loan loan = new Loan();
	private static LoanApplication loanApplication1 = new LoanApplication();
	private static LoanApplication loanApplication2 = new LoanApplication();
	private static LoanApplication loanApplication3 = new LoanApplication();

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
		client.setBankAccounts(Arrays.asList(bankAccount1));
		client.setBankAccounts(Arrays.asList(bankAccount2));
		client.setLoanApps(Arrays.asList(loanApplication1));
		client.setLoanApps(Arrays.asList(loanApplication2));

		bankAccount1.setId(1);
		bankAccount1.setAccountNumber("ES23 2323 2323 2323 2323");
		bankAccount1.setAmount(10000.0);
		bankAccount1.setCreatedAt(LocalDateTime.parse("2017-10-30T12:30:00"));
		bankAccount1.setAlias("Viajes");
		bankAccount1.setClient(client);
		bankAccount1.setLoanApps(Arrays.asList(loanApplication1));
		
		bankAccount2.setId(1);
		bankAccount2.setAccountNumber("ES45 4545 4545 4545 4545");
		bankAccount2.setAmount(100.0);
		bankAccount2.setCreatedAt(LocalDateTime.parse("2017-10-30T12:30:00"));
		bankAccount2.setAlias("Viajes");
		bankAccount2.setClient(client);
		bankAccount2.setLoanApps(Arrays.asList(loanApplication2));

		loan.setId(1);
		loan.setDescription("This is a Description");
		loan.setMinimum_amount(1000.0);
		loan.setMinimum_income(1000.0);
		loan.setNumber_of_deadlines(2);
		loan.setOpening_price(100.0);
		loan.setMonthly_fee(0.02);
		loan.setSingle_loan(true);
		loan.setLoanApplications(Arrays.asList(loanApplication1));
		loan.setLoanApplications(Arrays.asList(loanApplication2));
	}

	@BeforeEach
	private void reset() {
		loanApplication1.setId(1);
		loanApplication1.setAmount(2000.0);
		loanApplication1.setPurpose("This is a purpose");
		loanApplication1.setStatus("PENDING");
		loanApplication1.setAmount_paid(100.0);
		loanApplication1.setBankAccount(bankAccount1);
		loanApplication1.setLoan(loan);
		loanApplication1.setClient(client);
		loanApplication1.setPayedDeadlines(0);
		this.loanApplicationService.save(loanApplication1);
		
		loanApplication2.setId(2);
		loanApplication2.setAmount(2000.0);
		loanApplication2.setPurpose("This is a purpose");
		loanApplication2.setStatus("ACCEPTED");
		loanApplication2.setAmount_paid(100.0);
		loanApplication2.setBankAccount(bankAccount2);
		loanApplication2.setLoan(loan);
		loanApplication2.setClient(client);
		loanApplication2.setPayedDeadlines(0);
		
		loanApplication3.setId(3);
		loanApplication3.setAmount(2000.0);
		loanApplication3.setPurpose("This is a purpose");
		loanApplication3.setStatus("ACCEPTED");
		loanApplication3.setAmount_paid(100.0);
		loanApplication3.setBankAccount(bankAccount2);
		loanApplication3.setLoan(loan);
		loanApplication3.setPayedDeadlines(0);
	}

	@Test
	public void testCountLoanAppsAfterCreating() {
		Collection<ClientLoanApp> loanAplocations = this.loanApplicationService.findPendingsLoanApps();
		assertThat(loanAplocations.size()).isEqualTo(2);
	}

	@Test
	public void testCountLoanAppsByClient() {
		Collection<LoanApplication> loanAplocations = this.loanApplicationService.findLoanAppsByClient(client.getId());
		assertThat(loanAplocations.size()).isEqualTo(3);
	}

	@Test
	public void testSetAttributes() {
		this.loanApplicationService.setAttributes(bankAccount1.getId(), loan.getId(), loanApplication1);
		assertThat(loanApplication1.getStatus().equals("PENDING"));
		assertThat(loanApplication1.getAmount_paid().equals(0.0));
		assertThat(loanApplication1.getLoan().equals(loan));
		assertThat(loanApplication1.getBankAccount().equals(bankAccount1));
		assertThat(loanApplication1.getClient().equals(client));
	}

	@Test
	public void testAcceptLoanApplication() {
		this.loanApplicationService.acceptLoanApp(loanApplication1);
		assertThat(loanApplication1.getStatus().equals("ACCEPTED"));
	}

	@Test
	public void testRefuseLoanApplication() {
		this.loanApplicationService.refuseLoanApp(loanApplication1);
		assertThat(loanApplication1.getStatus().equals("REJECTED"));
	}

	@Test
	public void testCollectAcceptedLoanApp() {
		Client newClient = client;
		newClient.setDebts(Arrays.asList());
		newClient.setLoanApps(Arrays.asList(loanApplication3));
		loanApplication3.setClient(newClient);
		
		this.loanApplicationService.collectAcceptedLoans(Arrays.asList(loanApplication1, loanApplication2, loanApplication3));
		assertThat(loanApplication1.getAmount_paid().equals(loanApplication1.getAmountToPay()));
		assertThat(bankAccount1.getAmount().equals(bankAccount1.getAmount()+loanApplication1.getAmountToPay()));
	}

}
