package org.springframework.samples.acmerico.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.acmerico.model.BankAccount;
import org.springframework.samples.acmerico.model.Client;
import org.springframework.samples.acmerico.model.LoanApplication;
import org.springframework.samples.acmerico.repository.DashboardRepository;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {
	
	private DashboardRepository dashboardRepository;

	private ClientService clientService;
	
	@Autowired
	public DashboardService(ClientService clientService, DashboardRepository dashboardRepository) {
		this.clientService = clientService;
		this.dashboardRepository = dashboardRepository;
	}	
	
	public Map<String, List<Object>> amountsToPay(String username){
		Map<String, List<Object>> result = new HashMap<>();
		
		Client client = (Client) this.clientService.findClientByUserName(username);
		List<LoanApplication> loanApps = (List<LoanApplication>) client.getLoanApps();
		
		Double amountToPay;
		List<Object> amountsToPay = new ArrayList<>();
		List<Object> months = new ArrayList<>();
		
		for(int  i = 0; i<5; i++) {
			amountToPay = 0.;
			for(LoanApplication la : loanApps) {
				if((la.getLoan().getNumber_of_deadlines() - la.getPayedDeadlines())>i) {
					amountToPay = amountToPay + la.getAmountToPay();
				}
			}
			amountsToPay.add((double) Math.round(amountToPay*100)/100);
			LocalDate now = LocalDate.now();
			months.add(now.plusMonths(i).getMonth().toString());
		}
		
		result.put("amountsToPay", amountsToPay);
		result.put("months", months);
		
		return result;
	}
	
	public Map<String, List<Integer>> applicationsStatus(String username){
		Map<String, List<Integer>> result = new HashMap<>();
		
		Client client = (Client) this.clientService.findClientByUserName(username);
		
		Integer loanAppsPending = this.dashboardRepository.countLoanAppsPending(client);
		Integer loanAppsAccepted = this.dashboardRepository.countLoanAppsAccepted(client);
		Integer loanAppsRejected = this.dashboardRepository.countLoanAppsRejected(client);
		
		Integer creditCardAppsPending = this.dashboardRepository.countCreditCardAppsPending(client);
		Integer creditCardAppsAccepted = this.dashboardRepository.countCreditCardAppsAccepted(client);
		Integer creditCardAppsRejected = this.dashboardRepository.countCreditCardAppsRejected(client);
		
		Integer transferAppsPending = this.dashboardRepository.countTransferAppsPending(client);
		Integer transferAppsAccepted = this.dashboardRepository.countTransferAppsAccepted(client);
		Integer transferAppsRejected = this.dashboardRepository.countTransferAppsRejected(client);

		result.put("loanAppsStatus", Arrays.asList(loanAppsAccepted, loanAppsRejected, loanAppsPending));
		result.put("creditCardAppsStatus", Arrays.asList(creditCardAppsAccepted, creditCardAppsRejected, creditCardAppsPending));
		result.put("transferAppsStatus", Arrays.asList(transferAppsAccepted, transferAppsRejected, transferAppsPending));
		
		return result;
	}
	
	public Map<String, Double> moneyDebtPie(String username){
		Map<String, Double> result = new HashMap<>();
		
		Client client = (Client) this.clientService.findClientByUserName(username);

		Integer moneyToDebt = this.dashboardRepository.countMoneyToDebt(client);
		Integer moneyInBankAccounts = this.dashboardRepository.countMoneyInBankAccounts(client);
		
		result.put("moneyToDebt", (double) Math.round(moneyToDebt*100f)/100f);
		result.put("moneyInBankAccounts", (double) Math.round(moneyInBankAccounts*100f)/100f);

		return result;
	}
	
	public Map<String, List<Object>> myMoneyPie(String username){
		Map<String, List<Object>> result = new HashMap<>();
		List<Object> bankAccountAmounts = new ArrayList<>();
		List<Object> alias = new ArrayList<>();
		
		Client client = (Client) this.clientService.findClientByUserName(username);
		List<BankAccount> bankAccounts = (List<BankAccount>) client.getBankAccounts();
		
		for(BankAccount ba:bankAccounts) {
			bankAccountAmounts.add(Math.round(ba.getAmount()*100)/100);
			alias.add(ba.getAlias());
		}
		
		result.put("bankAccountAmounts", bankAccountAmounts);
		result.put("alias", alias);
		
		return result;
	}
	
}
