package org.springframework.samples.petclinic.web;

import java.security.Principal;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.CreditCardApplication;
import org.springframework.samples.petclinic.service.ClientService;
import org.springframework.samples.petclinic.service.CreditCardAppService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CreditCardAppController {
	
	private final ClientService clientService;

	private final CreditCardAppService creditCardAppService;

	@Autowired
	public CreditCardAppController(ClientService clientService, CreditCardAppService creditCardAppService) {
		this.clientService = clientService;
		this.creditCardAppService = creditCardAppService;
	}
	
	@RequestMapping(value = "/cardApps", method = RequestMethod.GET)
	public String showClientCardApps(Principal principal, Model model) {
		String username = principal.getName();
		Client client = this.clientService.findClientByUserName(username);
		Collection<CreditCardApplication> result = creditCardAppService.findCreditCardAppByClientId(client.getId());
		model.addAttribute("cardApps", result);
		model.addAttribute("clientUser", client.getUser().getUsername());
		return "cardApps/cardApps";
	}
	
}
