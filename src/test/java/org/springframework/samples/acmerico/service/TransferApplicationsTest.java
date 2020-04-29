package org.springframework.samples.acmerico.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
import org.springframework.samples.acmerico.model.TransferApplication;
import org.springframework.samples.acmerico.model.User;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class TransferApplicationsTest {

	@Autowired
	private TransferAppService transferAppService;
	
	@Autowired
	private BankAccountService bankAccountService;
	
	@Autowired
	private ClientService clientService;

	EntityManager entityManager;
	
	BankAccount bankAccount = new BankAccount();
	Client client = new Client();
	User user = new User();
	TransferApplication transferApp = new TransferApplication();

	@BeforeEach
	private void populateData() {
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
		
		transferApp.setStatus("PENDING");
		transferApp.setAmount(200.00);
		transferApp.setAccount_number_destination("ES24 2323 2323 2323 2323");
		transferApp.setBankAccount(bankAccount);
		transferApp.setClient(client);
		
		this.transferAppService.save(transferApp);
	}

	@Test
	public void testCountTransferApplicationsAfterCreating() {
		Collection<TransferApplication> transferApps = this.transferAppService.findAllTransfersApplications();
		assertThat(transferApps.size()).isEqualTo(20);
	}
	
	@Test
	public void testFindTransferApplicationsByClient() {
		Collection<TransferApplication> transferApps = this.transferAppService.findAllTransfersApplicationsByClient(client);
		assertThat(transferApps.size()).isEqualTo(1);
	}
	
	@Test
	public void testSetMoney() {
		this.transferAppService.setMoney(transferApp);
		assertThat(bankAccount.getAmount()).isEqualTo(99800.);
	}
	
	@Test
	public void testAcceptApp() {
		this.transferAppService.acceptApp(transferApp);
		assertThat(transferApp.getStatus()).isEqualTo("ACCEPTED");
	}
	
	@Test
	public void testRefuseApp() {
		this.transferAppService.refuseApp(transferApp);
		assertThat(transferApp.getStatus()).isEqualTo("REJECTED");
	}

	@Test
	public void testTransferWithoutMoneyInAccount() {
		transferApp.setAmount(102000.00);
		assertThrows(IllegalArgumentException.class, ()-> this.transferAppService.setMoney(transferApp));
	}

	@Test
	public void saveInvalidTransferApp() {
		transferApp.setAccount_number_destination("");
		assertThrows(NullPointerException.class, ()-> { this.transferAppService.save(transferApp); this.entityManager.flush(); });
	}
}
