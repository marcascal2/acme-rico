package org.springframework.samples.acmerico.web;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.acmerico.service.DashboardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

	private DashboardService dashboardService;

	@Autowired
	public DashboardController(DashboardService dashboardService) {
		this.dashboardService = dashboardService;
	}

	@GetMapping(value = "/dashboard")
	public String showClientCards(Principal principal, Model model) {
		String username = principal.getName();
		
		Map<String, List<Object>> applicationsStatus = this.dashboardService.applicationsStatus(username);
		
		model.addAttribute("loanAppsStatus", applicationsStatus.get("loanAppsStatus"));
		model.addAttribute("creditCardAppsStatus", applicationsStatus.get("creditCardAppsStatus"));
		model.addAttribute("transferAppsStatus", applicationsStatus.get("transferAppsStatus"));
		
		Map<String, List<Object>> amountsToPay = this.dashboardService.amountsToPay(username);

		model.addAttribute("months", amountsToPay.get("months"));
		model.addAttribute("amountsToPay", amountsToPay.get("amountsToPay"));
		
		return "dashboard/clientMoneyInfo";
	}
}
