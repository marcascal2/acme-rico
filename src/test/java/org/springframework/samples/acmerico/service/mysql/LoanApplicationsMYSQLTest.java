package org.springframework.samples.acmerico.service.mysql;

import java.util.Collection;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.acmerico.model.Client;
import org.springframework.samples.acmerico.model.LoanApplication;
import org.springframework.samples.acmerico.projections.ClientLoanApp;
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
		Collection<ClientLoanApp> loanAplocations = this.loanApplicationService.findPendingsLoanApps();
		Assertions.assertThat(loanAplocations.size()).isEqualTo(2);
	}

	@Test
	public void testCountLoanAppsByClient() {
		Client client = this.clientService.findClientById(1);
		Collection<LoanApplication> loanAplocations = this.loanApplicationService.findLoanAppsByClient(client.getId());
		Assertions.assertThat(loanAplocations.size()).isEqualTo(2);
	}

	@Test
	public void testAcceptLoanApplication() {
		LoanApplication loanApp = this.loanApplicationService.findLoanAppById(1);
		this.loanApplicationService.acceptLoanApp(loanApp);
		Assertions.assertThat(loanApp.getStatus().equals("ACCEPTED"));
	}

	@Test
	public void testRefuseLoanApplication() {
		LoanApplication loanApp = this.loanApplicationService.findLoanAppById(3);
		this.loanApplicationService.refuseLoanApp(loanApp);
		Assertions.assertThat(loanApp.getStatus().equals("REJECTED"));
	}

	@Test
	public void testCollectAcceptedLoanApp() {
		LoanApplication loanApp = this.loanApplicationService.findLoanAppById(1);
		Client client = this.clientService.findClientById(1);
		LoanApplication loanAppNoMoney = new LoanApplication();
		loanAppNoMoney.setAmount(1000000.0);
		loanAppNoMoney.setAmount_paid(0.);
		loanAppNoMoney.setPayedDeadlines(0);
		loanAppNoMoney.setPurpose("This is a purpose");
		loanAppNoMoney.setStatus("ACCEPTED");
		loanAppNoMoney.setLoan(loanApp.getLoan());
		loanAppNoMoney.setClient(client);
		loanAppNoMoney.setBankAccount(loanApp.getBankAccount());
		Collection<LoanApplication> loanApps = this.loanApplicationService.findLoanAppsAccepted();
		loanApps.add(loanAppNoMoney);
		this.loanApplicationService.collectAcceptedLoans(loanApps);
		Assertions.assertThat(loanApp.getAmount_paid().equals(loanApp.getAmountToPay()));
		Assertions.assertThat(loanApp.getBankAccount().getAmount().equals(loanApp.getBankAccount().getAmount()+loanApp.getAmountToPay()));
	}

}
