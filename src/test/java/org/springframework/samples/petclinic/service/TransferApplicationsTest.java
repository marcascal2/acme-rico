package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.BankAccount;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.TransferApplication;
import org.springframework.samples.petclinic.model.User;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class TransferApplicationsTest {

	@Autowired
	private TransferAppService transferAppService;
	
	private static BankAccount bankAccount = new BankAccount();
	private static Client client = new Client();
	private static User user = new User();
	
	@BeforeAll
	static void populateUser() {
		user.setUsername("userPrueba");
		user.setPassword("userPrueba");
		user.setEnabled(true);
	}
	
	@BeforeAll
	static void populateClient(){
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
	}
	
	@BeforeAll
	static void populateBankAccount(){
		bankAccount.setAccountNumber("ES23 2323 2323 2323 2323");
		bankAccount.setAmount(100000.0);
		bankAccount.setCreatedAt(LocalDateTime.parse("2017-10-30T12:30:00"));
		bankAccount.setAlias("Viajes");
		bankAccount.setClient(client);
	}
	
	@Test
	public void testCountTransferApplications() {
		Collection<TransferApplication> transferApps = this.transferAppService.findAllTransfersApplications();
		assertThat(transferApps.size()).isEqualTo(19);
	}

	@Test
	public void testCountTransferApplicationsAfterCreating() {
		TransferApplication transferApp = new TransferApplication();
		transferApp.setStatus("PENDING");
		transferApp.setAmount(200.00);
		transferApp.setAccount_number_destination("ES24 2323 2323 2323 2323");
		transferApp.setBankAccount(bankAccount);
		transferApp.setClient(client);
		this.transferAppService.save(transferApp);
		Collection<TransferApplication> transferApps = this.transferAppService.findAllTransfersApplications();
		assertThat(transferApps.size()).isEqualTo(20);
	}
	
}
