package org.springframework.samples.acmerico.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.acmerico.model.BankAccount;
import org.springframework.samples.acmerico.model.Client;
import org.springframework.samples.acmerico.model.CreditCard;
import org.springframework.samples.acmerico.model.User;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class CreditCardTest {
	
	@Autowired
	private ClientService clientService;

	@Autowired
	private BankAccountService bankAccountService;

	@Autowired
	private CreditCardService cardService;
	
	BankAccount bankAccount = new BankAccount();
	Client client = new Client();
	CreditCard cc = new CreditCard();
	
	EntityManager entityManager;

	@Before
	public void setUpData() {
		User user = new User();
		user.setUsername("user");
		user.setPassword("pass");
		
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

		this.clientService.saveClient(client);

		bankAccount.setAccountNumber("ES23 2323 2323 2323 2323");
		bankAccount.setAmount(100000.0);
		bankAccount.setCreatedAt(LocalDateTime.parse("2017-10-30T12:30:00"));
		bankAccount.setAlias("Viajes");
		bankAccount.setClient(client);

		this.bankAccountService.saveBankAccount(bankAccount);
		
		cc.setNumber("4095742744779740");
		cc.setDeadline("01/2021");
		cc.setCvv("334");
		cc.setBankAccount(bankAccount);
		cc.setClient(client);
		
		this.cardService.saveCreditCard(cc);
	}
	
	@Test
	public void testAllCards() {
		Collection<CreditCard> ccs = this.cardService.findAllCards();
		assertThat(ccs.size()).isEqualTo(12);
	}
	
	@Test
	public void testDeleteCard() {
		CreditCard card = this.cardService.findAllCards().stream().collect(Collectors.toList()).get(0);
		this.cardService.deleteCard(card.getId());
		Collection<CreditCard> ccs = this.cardService.findAllCards();
		assertThat(ccs.size()).isEqualTo(11);
	}
}
