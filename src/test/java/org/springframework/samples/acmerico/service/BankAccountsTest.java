package org.springframework.samples.acmerico.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

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
import org.springframework.samples.acmerico.model.User;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class BankAccountsTest {

	@Autowired
	private BankAccountService accountService;
	
	@Autowired
	private ClientService clientService;

	BankAccount bankAccount = new BankAccount();
	Client client = new Client();
	EntityManager entityManager;

	@BeforeEach
	void populateData() {
		User user = new User();
		user.setUsername("user");
		user.setPassword("pass");
		client.setFirstName("Emilia");
		client.setMaritalStatus("single");
		client.setBirthDate(LocalDate.of(2020, 1, 1));
		client.setJob("student");
		client.setSalaryPerYear(200.00);
		client.setLastName("Coleto");
		client.setCity("Sevilla");
		client.setAge(20);
		client.setAddress("address");
		client.setUser(user);
		client.setLastEmployDate(null);
		this.clientService.saveClient(client);
		bankAccount.setAccountNumber("ES23 2323 2323 2323 2323");
		bankAccount.setAlias("alias");
		bankAccount.setAmount(200.00);
		bankAccount.setCreatedAt(LocalDateTime.of(2020, 2, 1, 17, 30));
		bankAccount.setClient(client);
		this.accountService.saveBankAccount(bankAccount);
	}

	@Test
	public void testCountBankAccountsByClient() {
		Collection<BankAccount> bankAccountsList = this.accountService.findBankAccountByClient(client);
		assertThat(bankAccountsList.size()).isEqualTo(1);
	}

	@Test
	public void testCountBankAccountsByAccountNumber() {
		Collection<BankAccount> bankAccountsList = this.accountService
				.findBankAccountByAccountNumber(bankAccount.getAccountNumber());
		assertThat(bankAccountsList.size()).isEqualTo(1);

	}

	@Test
	public void testCountBankAccountsAfterCreating() {
		Collection<BankAccount> accounts = this.accountService.findBankAccounts();
		assertThat(accounts.size()).isEqualTo(11);
	}

	@Test
	public void testDeleteBankAccount() {
		this.accountService.deleteAccount(bankAccount);
		Collection<BankAccount> accounts = this.accountService.findBankAccounts();
		assertThat(accounts.size()).isEqualTo(10);
	}

	@Test
	public void testFindBankAccountByNumber() {
		BankAccount b = this.accountService.findBankAccountByNumber(bankAccount.getAccountNumber());
		assertThat(b).isEqualTo(bankAccount);
	}

	@Test
	public void testSumAmount() {
		Double transferAmount = 40.;
		this.accountService.sumAmount(transferAmount, bankAccount);
		assertThat(bankAccount.getAmount()).isEqualTo(240.);
	}

	@Test
	public void testSubstractAmount() {
		Double tranferAmount = 40.;
		this.accountService.substractAmount(tranferAmount, bankAccount);
		assertThat(bankAccount.getAmount()).isEqualTo(160.);
	}

	@Test
	public void saveInvalidBankAccount() {
		bankAccount.setAlias("aliasaliasaliasaliasaliasaliasaliasalias");
		assertThrows(NullPointerException.class, () -> {
			this.accountService.saveBankAccount(bankAccount);
			this.entityManager.flush();
		});
	}
}
