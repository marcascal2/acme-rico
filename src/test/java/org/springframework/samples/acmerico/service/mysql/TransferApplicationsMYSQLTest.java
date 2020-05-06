package org.springframework.samples.acmerico.service.mysql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collection;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.acmerico.model.Client;
import org.springframework.samples.acmerico.model.TransferApplication;
import org.springframework.samples.acmerico.service.ClientService;
import org.springframework.samples.acmerico.service.TransferAppService;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class TransferApplicationsMYSQLTest {

	@Autowired
	private TransferAppService transferAppService;
	
	@Autowired
	private ClientService clientService;

	EntityManager entityManager;

	@Test
	public void testCountTransferApplicationsAfterCreating() {
		Collection<TransferApplication> transferApps = this.transferAppService.findAllTransfersApplications();
		assertThat(transferApps.size()).isEqualTo(19);
	}
	
	@Test
	public void testFindTransferApplicationsByClient() {
		Client client = this.clientService.findClientById(1);
		Collection<TransferApplication> transferApps = this.transferAppService.findAllTransfersApplicationsByClient(client);
		assertThat(transferApps.size()).isEqualTo(6);
	}
	
	@Test
	public void testSetMoney() {
		TransferApplication transferApp = this.transferAppService.findTransferAppById(4);
		this.transferAppService.setMoney(transferApp);
		assertThat(transferApp.getBankAccount().getAmount()).isEqualTo(8800.0);
	}
	
	@Test
	public void testAcceptApp() {
		TransferApplication transferApp = this.transferAppService.findTransferAppById(11);
		this.transferAppService.acceptApp(transferApp);
		assertThat(transferApp.getStatus()).isEqualTo("ACCEPTED");
	}
	
	@Test
	public void testRefuseApp() {
		TransferApplication transferApp = this.transferAppService.findTransferAppById(14);
		this.transferAppService.refuseApp(transferApp);
		assertThat(transferApp.getStatus()).isEqualTo("REJECTED");
	}

	@Test
	public void testTransferWithoutMoneyInAccount() {
		TransferApplication transferApp = this.transferAppService.findTransferAppById(14);
		transferApp.setAmount(102000.00);
		assertThrows(IllegalArgumentException.class, ()-> this.transferAppService.setMoney(transferApp));
	}

	@Test
	public void saveInvalidTransferApp() {
		TransferApplication transferApp = this.transferAppService.findTransferAppById(14);
		transferApp.setAccount_number_destination("");
		assertThrows(NullPointerException.class, ()-> { this.transferAppService.save(transferApp); this.entityManager.flush(); });
	}
}
