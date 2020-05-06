package org.springframework.samples.acmerico.service.mysql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.acmerico.model.BankAccount;
import org.springframework.samples.acmerico.model.Client;
import org.springframework.samples.acmerico.service.BankAccountService;
import org.springframework.samples.acmerico.service.ClientService;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.DirtiesContext;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace=Replace.NONE)
@DirtiesContext
public class BankAccountsMYSQLTest {

	@Autowired
	private BankAccountService accountService;
	
	@Autowired
	private ClientService clientService;

	EntityManager entityManager;

	@Test
	public void testCountBankAccountsByClient() {
		Client client = this.clientService.findClientById(1);
		Collection<BankAccount> bankAccountsList = this.accountService.findBankAccountByClient(client);
		assertThat(bankAccountsList.size()).isEqualTo(3);
	}

	@Test
	public void testCountBankAccountsByAccountNumber() {
		Collection<BankAccount> bankAccountsList = this.accountService
				.findBankAccountByAccountNumber("ES23 0025 0148 1259 1424");
		assertThat(bankAccountsList.size()).isEqualTo(1);

	}

	@Test
	public void testCountBankAccountsAfterCreating() {
		BankAccount bankAccount = new BankAccount();
		Client client = this.clientService.findClientById(2);
		bankAccount.setAccountNumber("ES23 2323 2323 2323 2320");
		bankAccount.setAlias("alias");
		bankAccount.setAmount(200.00);
		bankAccount.setCreatedAt(LocalDateTime.of(2020, 2, 1, 17, 30));
		bankAccount.setClient(client);
		this.accountService.saveBankAccount(bankAccount);
		Collection<BankAccount> accounts = this.accountService.findBankAccounts();
		assertThat(accounts.size()).isEqualTo(11);
	}

	@Test
	public void testDeleteBankAccount() {
		Client client = this.clientService.findClientById(1);
		BankAccount newBankAccount = new BankAccount();
		newBankAccount.setAccountNumber("ES23 2323 2323 2323 1111");
		newBankAccount.setAmount(0.);
		newBankAccount.setClient(client);
		newBankAccount.setCreatedAt(LocalDateTime.now());
		this.accountService.saveBankAccount(newBankAccount);
		this.accountService.deleteAccount(newBankAccount);
		Collection<BankAccount> accounts = this.accountService.findBankAccounts();
		assertThat(accounts.size()).isEqualTo(11);
	}

	@Test
	public void testSumAmount() {
		BankAccount bankAccount = this.accountService.findBankAccounts().stream().collect(Collectors.toList()).get(0);
		Double transferAmount = 40.;
		this.accountService.sumAmount(transferAmount, bankAccount);
		assertThat(bankAccount.getAmount()).isEqualTo(2607.34);
	}

	@Test
	public void testSubstractAmount() {
		BankAccount bankAccount = this.accountService.findBankAccounts().stream().collect(Collectors.toList()).get(0);
		Double tranferAmount = 40.;
		this.accountService.substractAmount(tranferAmount, bankAccount);
		assertThat(bankAccount.getAmount()).isEqualTo(2527.34);
	}

	@Test
	public void saveInvalidBankAccount() {
		BankAccount bankAccount = new BankAccount();
		Client client = this.clientService.findClientById(2);
		bankAccount.setAccountNumber("ES23 2323 2323 2323 2323");
		bankAccount.setAlias("aliasaliasaliasaliasaliasaliasaliasalias");
		bankAccount.setAmount(200.00);
		bankAccount.setCreatedAt(LocalDateTime.of(2020, 2, 1, 17, 30));
		bankAccount.setClient(client);
		assertThrows(ConstraintViolationException.class, () -> {
			this.accountService.saveBankAccount(bankAccount);
			this.entityManager.flush();
		});
	}
}
