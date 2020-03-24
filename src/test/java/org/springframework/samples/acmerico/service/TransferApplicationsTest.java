package org.springframework.samples.acmerico.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.acmerico.model.BankAccount;
import org.springframework.samples.acmerico.model.Client;
import org.springframework.samples.acmerico.model.TransferApplication;
import org.springframework.samples.acmerico.model.User;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class TransferApplicationsTest {

	@Autowired
	private TransferAppService transferAppService;
	
	BankAccount bankAccount = new BankAccount();
	Client client = new Client();
	User user = new User();
	
	@BeforeEach
	private void populateData() {
		TransferApplication transferApp = new TransferApplication();
		
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
		
		bankAccount.setAccountNumber("ES23 2323 2323 2323 2323");
		bankAccount.setAmount(100000.0);
		bankAccount.setCreatedAt(LocalDateTime.parse("2017-10-30T12:30:00"));
		bankAccount.setAlias("Viajes");
		bankAccount.setClient(client);
		
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
}
