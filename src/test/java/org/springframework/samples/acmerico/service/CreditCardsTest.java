package org.springframework.samples.acmerico.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.acmerico.model.BankAccount;
import org.springframework.samples.acmerico.model.Client;
import org.springframework.samples.acmerico.model.CreditCard;
import org.springframework.samples.acmerico.model.CreditCardApplication;
import org.springframework.samples.acmerico.model.User;
import org.springframework.stereotype.Service;

@Disabled

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class CreditCardsTest {

	@Autowired
	private CreditCardService creditCardService;
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

	CreditCard creditCard = new CreditCard();

	@BeforeEach
	void populateData() {
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
		
		creditCard.setBankAccount(bankAccount);
		creditCard.setClient(client);
		creditCard.setCreditCardApplication(application);
		creditCard.setCvv("000");
		creditCard.setDeadline("04/2020");
		creditCard.setNumber("4295742384950740");
		this.creditCardService.saveCreditCard(creditCard);
	}

	@Test
	public void testCountCreditCardAfterCreating() {
		List<CreditCard> cards = (List<CreditCard>) this.creditCardService.findCreditCards();
		assertThat(cards.size()).isEqualTo(5);
	}

	@Test
	public void testDeleteCreditCard() {
		this.creditCardService.deleteCreditCardById(creditCard.getId());
		Collection<CreditCard> cards = this.creditCardService.findCreditCards();
		assertThat(cards.size()).isEqualTo(4);
	}

}
