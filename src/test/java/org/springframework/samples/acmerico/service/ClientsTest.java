package org.springframework.samples.acmerico.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.acmerico.model.Client;
import org.springframework.samples.acmerico.model.User;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ClientsTest {

	@Autowired
	private ClientService service;

	Client new_client = new Client();
	
	@BeforeEach
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

}
