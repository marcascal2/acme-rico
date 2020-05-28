package org.springframework.samples.acmerico.service.mysql;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.acmerico.model.Client;
import org.springframework.samples.acmerico.model.LoanApplication;
import org.springframework.samples.acmerico.service.ClientService;
import org.springframework.samples.acmerico.service.LoanAppService;
import org.springframework.stereotype.Service;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class LoanApplicationsMYSQLTest {

	@Autowired
	private LoanAppService loanApplicationService;
	
	@Autowired
	private ClientService clientService;

	@Test
	public void testCountLoanAppsAfterCreating() {
		Collection<LoanApplication> loanAplocations = this.loanApplicationService.findPendingsLoanApps();
		assertThat(loanAplocations.size()).isEqualTo(2);
	}

	@Test
	public void testCountLoanAppsByClient() {
		Client client = this.clientService.findClientById(1);
		Collection<LoanApplication> loanAplocations = this.loanApplicationService.findLoanAppsByClient(client.getId());
		assertThat(loanAplocations.size()).isEqualTo(1);
	}

	@Test
	public void testAcceptLoanApplication() {
		LoanApplication loanApp = this.loanApplicationService.findLoanAppById(1);
		this.loanApplicationService.acceptLoanApp(loanApp);
		assertThat(loanApp.getStatus().equals("ACCEPTED"));
	}

	@Test
	public void testRefuseLoanApplication() {
		LoanApplication loanApp = this.loanApplicationService.findLoanAppById(3);
		this.loanApplicationService.refuseLoanApp(loanApp);
		assertThat(loanApp.getStatus().equals("REJECTED"));
	}

	@Test
	public void testCollectAcceptedLoanApp() {
		LoanApplication loanApp = this.loanApplicationService.findLoanAppById(1);
		this.loanApplicationService.collectAcceptedLoans(this.loanApplicationService.findLoanAppsAccepted());
		assertThat(loanApp.getAmount_paid().equals(loanApp.getAmountToPay()));
		assertThat(loanApp.getBankAccount().getAmount().equals(loanApp.getBankAccount().getAmount()+loanApp.getAmountToPay()));
	}

}
