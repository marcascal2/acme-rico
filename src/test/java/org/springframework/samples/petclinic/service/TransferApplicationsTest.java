package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.TransferApplication;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class TransferApplicationsTest {

	@Autowired
	private TransferAppService transferAppService;
	
	@Test
	public void testCountTransferApplications() {
		Collection<TransferApplication> transferApps = this.transferAppService.findAllTransfersApplications();
		assertThat(transferApps.size()).isEqualTo(0);
	}

	@Test
	public void testCountTransferApplicationsAfterCreating() {
		TransferApplication transferApp = new TransferApplication();
		transferApp.setStatus("PENDING");
		transferApp.setAmount(200.00);
		transferApp.setAccount_number_destination("ES24 2323 2323 2323 2323");
		this.transferAppService.save(transferApp);
		Collection<TransferApplication> transferApps = this.transferAppService.findAllTransfersApplications();
		assertThat(transferApps.size()).isEqualTo(1);
	}
	
	@Test
	public void testCountTransferApplicationsById() {
		TransferApplication transferApp = new TransferApplication();
		transferApp.setStatus("PENDING");
		transferApp.setAmount(200.00);
		transferApp.setAccount_number_destination("ES24 2323 2323 2323 2323");
		this.transferAppService.save(transferApp);
		TransferApplication t = this.transferAppService.findTransferAppById(transferApp.getId());
		assertThat(t.getId()).isEqualTo(transferApp.getId());	
	}
}
