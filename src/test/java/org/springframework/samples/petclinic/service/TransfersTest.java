package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Transfer;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class TransfersTest {

	@Autowired
	private TransferService transferService;
	
	@Test
	public void testCountTransfers() {
		Collection<Transfer> transfers = this.transferService.findAllTransfers();
		assertThat(transfers.size()).isEqualTo(0);
	}

	@Test
	public void testCountTransfersAfterCreating() {
		Transfer transfer = new Transfer();
		transfer.setAccountNumber("ES23 2323 2323 2323 2323");
		transfer.setAmount(200.00);
		transfer.setDestination("ES24 2323 2323 2323 2323");
		this.transferService.save(transfer);
		Collection<Transfer> accounts = this.transferService.findAllTransfers();
		assertThat(accounts.size()).isEqualTo(1);
	}
	
}
