package org.springframework.samples.acmerico.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.acmerico.model.BankAccount;
import org.springframework.samples.acmerico.model.Client;
import org.springframework.samples.acmerico.model.CreditCardApplication;
import org.springframework.samples.acmerico.model.User;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class CreditCardApplicationTest {

	@Autowired
	private CreditCardAppService creditCardAppService;

	@Autowired
	private ClientService clientService;

	@Autowired
	private BankAccountService bankAccountService;

	BankAccount bankAccount = new BankAccount();
	Client client = new Client();
	User user = new User();
	CreditCardApplication application = new CreditCardApplication();
	EntityManager entityManager;

	@BeforeEach
	private void setUpData() {
		user.setUsername("userPrueba");
		user.setPassword("userPrueba");
		user.setEnabled(true);

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
		client.setBankAccounts(new ArrayList<BankAccount>());
		client.getBankAccounts().add(bankAccount);

		this.clientService.saveClient(client);

		bankAccount.setAccountNumber("ES23 2323 2323 2323 2323");
		bankAccount.setAmount(100000.0);
		bankAccount.setCreatedAt(LocalDateTime.parse("2017-10-30T12:30:00"));
		bankAccount.setAlias("Viajes");
		bankAccount.setClient(client);

		this.bankAccountService.saveBankAccount(bankAccount);

		application.setStatus("PENDING");
		application.setClient(client);
		application.setBankAccount(bankAccount);

		this.creditCardAppService.save(application);
	}

	@Test
	public void testCountCreditCardAppsAfterCreating() {
		List<CreditCardApplication> apps = (List<CreditCardApplication>) this.creditCardAppService.findCreditCardApps();
		assertThat(apps.size()).isEqualTo(10);
	}

	@Test
	public void testFindCreditCardApplicationsByClient() {
		Collection<CreditCardApplication> apps = this.creditCardAppService.findCreditCardAppByClientId(client.getId());
		assertThat(apps.size()).isEqualTo(1);
	}
	
	@Test
	public void testAcceptCreditCardApplication() {
		this.creditCardAppService.acceptApp(application);
		assertThat(application.getStatus()).isEqualTo("ACCEPTED");
	}
	
	@Test
	public void testRefuseCreditCardApplication() {
		this.creditCardAppService.refuseApp(application);
		assertThat(application.getStatus()).isEqualTo("REJECTED");
	}

	@Test
	public void createInvalidCreditCardApp() {
		application.setStatus("");
		assertThrows(NullPointerException.class, ()-> { this.creditCardAppService.save(application); this.entityManager.flush(); });
	}
}
