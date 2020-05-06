package org.springframework.samples.acmerico.service.mysql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collection;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.acmerico.model.BankAccount;
import org.springframework.samples.acmerico.model.Client;
import org.springframework.samples.acmerico.model.CreditCardApplication;
import org.springframework.samples.acmerico.service.ClientService;
import org.springframework.samples.acmerico.service.CreditCardAppService;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Transactional
public class CreditCardApplicationMYSQLTest {

	@Autowired
	private CreditCardAppService creditCardAppService;

	@Autowired
	private ClientService clientService;

	EntityManager entityManager;

	@Test
	public void testFindCreditCardApplicationsByClient() {
		Collection<CreditCardApplication> apps = this.creditCardAppService.findCreditCardAppByClientId(1);
		assertThat(apps.size()).isEqualTo(2);
	}

	@Test
	public void testAcceptCreditCardApplication() {
		CreditCardApplication ccApp = this.creditCardAppService.findCreditCardAppById(8);
		this.creditCardAppService.acceptApp(ccApp);
		assertThat(ccApp.getStatus()).isEqualTo("ACCEPTED");
	}

	@Test
	public void testRefuseCreditCardApplication() {
		CreditCardApplication ccApp = this.creditCardAppService.findCreditCardAppById(9);
		this.creditCardAppService.refuseApp(ccApp);
		assertThat(ccApp.getStatus()).isEqualTo("REJECTED");
	}

	@Test
	public void createInvalidCreditCardApp() {
		CreditCardApplication application = new CreditCardApplication();
		Client client = this.clientService.findClientById(4);
		BankAccount bankAccount = client.getBankAccounts().stream().collect(Collectors.toList()).get(0);
		application.setClient(client);
		application.setBankAccount(bankAccount);
		application.setStatus("");
		assertThrows(ConstraintViolationException.class, () -> {
			this.creditCardAppService.save(application);
			this.entityManager.flush();
		});
	}

	@Test
	public void testCreditCardAppNumberRestriction() {
		Client client = this.clientService.findClientById(1);
		BankAccount bankAccount = client.getBankAccounts().stream().collect(Collectors.toList()).get(0);
		CreditCardApplication ccApp1 = new CreditCardApplication();
		ccApp1.setBankAccount(bankAccount);
		ccApp1.setClient(client);
		ccApp1.setStatus("PENDING");

		CreditCardApplication ccApp2 = new CreditCardApplication();
		ccApp2.setBankAccount(bankAccount);
		ccApp2.setClient(client);
		ccApp2.setStatus("PENDING");

		CreditCardApplication ccApp3 = new CreditCardApplication();
		ccApp3.setBankAccount(bankAccount);
		ccApp3.setClient(client);
		ccApp3.setStatus("PENDING");

		CreditCardApplication ccApp4 = new CreditCardApplication();
		ccApp4.setBankAccount(bankAccount);
		ccApp4.setClient(client);
		ccApp4.setStatus("PENDING");

		CreditCardApplication ccApp5 = new CreditCardApplication();
		ccApp5.setBankAccount(bankAccount);
		ccApp5.setClient(client);
		ccApp5.setStatus("PENDING");

		this.creditCardAppService.save(ccApp1);
		this.creditCardAppService.save(ccApp2);
		this.creditCardAppService.save(ccApp3);
		this.creditCardAppService.save(ccApp4);
		this.creditCardAppService.save(ccApp5);

		Collection<CreditCardApplication> apps = this.creditCardAppService.findCreditCardAppByClientId(1);
		assertThat(apps.stream().filter(x -> x.getStatus().equals("PENDING")).collect(Collectors.toList()).size())
				.isEqualTo(3);
	}
}
