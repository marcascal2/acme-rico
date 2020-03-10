package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.BankAccount;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.Employee;
import org.springframework.samples.petclinic.model.User;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ClientsTest {
	
	@Autowired
	private ClientService service;
	
	@Test
	public void testCountClients() {
		Collection<Client> clients = this.service.findClientByLastName("");
		assertThat(clients.size()).isEqualTo(5);
	}

	@Test
	public void testCountClientsAfterCreating() {
		Client new_client = new Client();
		User new_user = new User();
		LocalDate bday = LocalDate.of(1999, 9, 6);
		LocalDate lastEmployDate = LocalDate.of(2019, 8, 23);
		
		new_user.setUsername("clientUser");
		new_user.setPassword("clientPass");
		new_user.setEnabled(true);
		new_client.setFirstName("Maria");
		new_client.setLastName("Casasola");
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
		
		Collection<Client> clients = this.service.findClientByLastName("");
		assertThat(clients.size()).isEqualTo(6);
	}
	
	@Test
	public void testCountClientsByLastName() {
		Client new_client = new Client();
		User new_user = new User();
		LocalDate bday = LocalDate.of(1999, 9, 6);
		LocalDate lastEmployDate = LocalDate.of(2019, 8, 23);
		
		new_user.setUsername("clientUser");
		new_user.setPassword("clientPass");
		new_user.setEnabled(true);
		new_client.setFirstName("Maria");
		new_client.setLastName("Casasola");
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
		
		Collection<Client> clients = this.service.findClientByLastName("Casasola");
		assertThat(clients.size()).isEqualTo(1);
	}
	
	@Test
	public void testCountClientsByUserName() {
		Client new_client = new Client();
		User new_user = new User();
		LocalDate bday = LocalDate.of(1999, 9, 6);
		LocalDate lastEmployDate = LocalDate.of(2019, 8, 23);
		
		new_user.setUsername("clientUser");
		new_user.setPassword("clientPass");
		new_user.setEnabled(true);
		new_client.setFirstName("Maria");
		new_client.setLastName("Casasola");
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
		Client client = (Client) this.service.findClientByUserName("clientUser");
		assertThat(client.getFirstName()).isEqualTo("Maria");
	}
	
}
