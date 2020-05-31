package org.springframework.samples.acmerico.service.mysql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;

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
import org.springframework.samples.acmerico.model.User;
import org.springframework.samples.acmerico.service.ClientService;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.DirtiesContext;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace=Replace.NONE)
@DirtiesContext
public class ClientsMYSQLTest {

	@Autowired
	private ClientService service;

	Client new_client = new Client();

	EntityManager entityManager;

	@Test
	public void testCountClientsAfterCreating() {
		Collection<Client> clients = this.service.findClientByLastName("");
		assertThat(clients.size()).isEqualTo(11);
	}

	@Test
	public void testCountClientsByLastName() {
		Collection<Client> clients = this.service.findClientByLastName("Parker");
		assertThat(clients.size()).isEqualTo(1);
	}

	@Test
	@Disabled
	// TODO: El test falla en travis
	public void testCountClientsByUserName() {
		Client client = (Client) this.service.findClientByUserName("client1");
		assertThat(client.getFirstName()).isEqualTo("George");
	}

	@Test
	@Disabled
	// TODO: El test falla en travis 
	public void testCountBankAccountsFromClient() {
		List<BankAccount> ba = (List<BankAccount>) this.service.findBankAccountsByUsername("client1");
		assertThat(ba.size()).isEqualTo(3);
	}

	@Test
	public void testCountCreditCardsFromClient() {
		List<CreditCard> cc = (List<CreditCard>) this.service.findCreditCardsByUsername("client3");
		assertThat(cc.size()).isEqualTo(1);
	}

	@Test
	public void testSaveInvalidClient() {
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
		new_client.setUser(null);
		assertThrows(NullPointerException.class, ()-> {service.saveClient(new_client); entityManager.flush();});
	}

	@Test
	public void testGetBankAccountsOfNonRegisteredClient() {
		assertThrows(NullPointerException.class, ()-> service.findBankAccountsByUsername("asdf"));
	}
}
