package org.springframework.samples.acmerico.web;

import java.security.Principal;
import java.util.Arrays;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {
	
//	private ClientService clientService;
//	
//	@Autowired
//	public DashboardController(ClientService clientService) {
//		this.clientService = clientService;
//	}

	@GetMapping(value = "/dashboard")
	public String showClientCards(Principal principal, Model model) {
//		String username = principal.getName();
//		Client client = (Client) this.clientService.findClientByUserName(username);
		
		model.addAttribute("moneyPerDays",  Arrays.asList("7","6","5","4","3","2","9"));
		model.addAttribute("labels", Arrays.asList("7","6","5","4","3","2","1"));
		
		return "dashboard/clientMoneyInfo";
	}
}
