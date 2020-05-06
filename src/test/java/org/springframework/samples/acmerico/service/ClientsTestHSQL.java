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
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.acmerico.model.BankAccount;
import org.springframework.samples.acmerico.model.Client;
import org.springframework.samples.acmerico.model.CreditCard;
import org.springframework.samples.acmerico.model.User;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.DirtiesContext;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class ClientsTestHSQL {

	@Autowired
	private ClientService service;

	Client new_client = new Client();

	EntityManager entityManager;

	@BeforeEach
	@DirtiesContext
	private void populateData() {
		User new_user = new User();
		LocalDate bday = LocalDate.of(1999, 9, 6);
		LocalDate lastEmployDate = LocalDate.of(2019, 8, 23);

		new_user.setUsername("clientUser");
		new_user.setPassword("clientPass");
		new_user.setEnabled(true);
		new_client.setFirstName("Name");
		new_client.setLastName("Surname");
		new_client.setAddress("C/ Teba, nº 4");
		new_client.setAge(20);
		new_client.setBirthDate(bday);
		new_client.setCity("Sevilla");
		new_client.setJob("Empresaria");
		new_client.setLastEmployDate(lastEmployDate);
		new_client.setMaritalStatus("Casada");
		new_client.setSalaryPerYear(20000.00);
		new_client.setUser(new_user);

		this.service.saveClient(new_client);

	}

	@Test
	public void testCountClientsAfterCreating() {
		Collection<Client> clients = this.service.findClientByLastName("");
		assertThat(clients.size()).isEqualTo(11);
	}

	@Test
	public void testCountClientsByLastName() {
		Collection<Client> clients = this.service.findClientByLastName("Surname");
		assertThat(clients.size()).isEqualTo(1);
	}

	@Test
	public void testCountClientsByUserName() {
		Client client = (Client) this.service.findClientByUserName("clientUser");
		assertThat(client.getFirstName()).isEqualTo("Name");
	}

	@Test
	public void testCountBankAccountsFromClient() {
		BankAccount account = new BankAccount();

		account.setAccountNumber("ES23 2323 2323 2323 2323");
		account.setAlias("alias");
		account.setAmount(200.00);
		account.setCreatedAt(LocalDateTime.of(2020, 2, 1, 17, 30));
		account.setClient(new_client);

		List<BankAccount> accounts = new ArrayList<BankAccount>();
		accounts.add(account);

		new_client.setBankAccounts(accounts);

		String username = new_client.getUser().getUsername();
		List<BankAccount> ba = (List<BankAccount>) this.service.findBankAccountsByUsername(username);
		assertThat(ba.size()).isEqualTo(1);
	}

	@Test
	public void testCountCreditCardsFromClient() {
		BankAccount account = new BankAccount();

		account.setAccountNumber("ES23 2323 2323 2323 2323");
		account.setAlias("alias");
		account.setAmount(200.00);
		account.setCreatedAt(LocalDateTime.of(2020, 2, 1, 17, 30));
		account.setClient(new_client);
		
		CreditCard card = new CreditCard();

		card.setNumber("5130218133680652");
		card.setDeadline("07/2022");
		card.setCvv("298");
		card.setBankAccount(account);
		card.setClient(new_client);

		List<CreditCard> cards = new ArrayList<CreditCard>();
		cards.add(card);

		new_client.setCreditCards(cards);
		
		String username = new_client.getUser().getUsername();
		List<CreditCard> cc = (List<CreditCard>) this.service.findCreditCardsByUsername(username);
		assertThat(cc.size()).isEqualTo(1);
	}

	@Test
	public void testSaveInvalidClient() {
		new_client.setUser(null);
		assertThrows(NullPointerException.class, ()-> {service.saveClient(new_client); entityManager.flush();});
	}

	@Test
	public void testGetBankAccountsOfNonRegisteredClient() {
		assertThrows(NullPointerException.class, ()-> service.findBankAccountsByUsername("asdf"));
	}
}
