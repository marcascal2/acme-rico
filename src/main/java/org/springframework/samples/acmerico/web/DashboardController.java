package org.springframework.samples.acmerico.web;

import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.acmerico.model.Client;
import org.springframework.samples.acmerico.model.LoanApplication;
import org.springframework.samples.acmerico.service.ClientService;
import org.springframework.samples.acmerico.service.DashboardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class DashboardController {

	private DashboardService dashboardService;
	
	private final ClientService clientService;

	@Autowired
	public DashboardController(DashboardService dashboardService, ClientService clientService) {
		this.dashboardService = dashboardService;
		this.clientService = clientService;

	}

	@GetMapping(value = "/dashboard")
	public String showClientCards(Principal principal, Model model) {
		String username = principal.getName();
		
		Map<String, List<Integer>> applicationsStatus = this.dashboardService.applicationsStatus(username);
		
		model.addAttribute("loanAppsStatus", applicationsStatus.get("loanAppsStatus"));
		model.addAttribute("creditCardAppsStatus", applicationsStatus.get("creditCardAppsStatus"));
		model.addAttribute("transferAppsStatus", applicationsStatus.get("transferAppsStatus"));
		
		Map<String, List<Object>> amountsToPay = this.dashboardService.amountsToPay(username);

		model.addAttribute("months", amountsToPay.get("months"));
		model.addAttribute("amountsToPay", amountsToPay.get("amountsToPay"));
		
		Map<String, Double> moneyDebtPie = this.dashboardService.moneyDebtPie(username);
		
		model.addAttribute("moneyToDebt", moneyDebtPie.get("moneyToDebt"));
		model.addAttribute("moneyInBankAccounts", moneyDebtPie.get("moneyInBankAccounts"));
		
		Map<String, List<Object>> myMoneyPie = this.dashboardService.myMoneyPie(username);
		
		model.addAttribute("bankAccountAmounts", myMoneyPie.get("bankAccountAmounts"));
		model.addAttribute("alias", myMoneyPie.get("alias"));
		
		return "dashboard/clientMoneyInfo";
	}
	
	@GetMapping(value = "/dashboard/{clientId}/statics")
	public String showClientsStatics(@PathVariable("clientId") int clientId, ModelMap modelMap) {
		
		Client client = this.clientService.findClientById(clientId);
		Integer countAccounts = client.getBankAccounts().size();
		
		Collection<LoanApplication> clientLoans = client.getLoanApps();
		Integer borrowedAmount = (int) clientLoans.stream().filter(x->x.getStatus().equals("ACCEPTED"))
				.mapToDouble(x->x.getAmount()).sum();
		
		Integer totalAmount = (int) client.getBankAccounts().stream().mapToDouble(x->x.getAmount()).sum();
			
		modelMap.addAttribute("countAccounts", countAccounts);
		modelMap.addAttribute("clientLoans",clientLoans);
		modelMap.addAttribute("borrowedAmount", borrowedAmount);
		modelMap.addAttribute("totalAmount", totalAmount);
		
		return "dashboard/clientStatics";
		
	}
}
