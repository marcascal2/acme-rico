package org.springframework.samples.petclinic.web;

import java.security.Principal;
import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.CreditCard;
import org.springframework.samples.petclinic.service.ClientService;
import org.springframework.samples.petclinic.service.CreditCardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CreditCardController {

	private static final String VIEWS_CARD_DETAILS = "cards/showCardInfo";

	private final ClientService clientService;

	private final CreditCardService creditCardService;

	@Autowired
	public CreditCardController(ClientService clientService, CreditCardService creditCardService) {
		this.clientService = clientService;
		this.creditCardService = creditCardService;
	}

	@RequestMapping(value = "/cards", method = RequestMethod.GET)
	public String showClientCards(Principal principal, Model model) {
		String username = principal.getName();
		Client client = this.clientService.findClientByUserName(username);
		Collection<CreditCard> result = clientService.findCreditCardsByUsername(username);
		System.out.println(result);
		System.out.println(client.getId());
		model.addAttribute("cards", result);
		model.addAttribute("clientId", client.getId());
		return "cards/cards";
	}

	@GetMapping(value = "/cards/{cardId}/show")
	public String showAccountInfo(@PathVariable("cardId") int cardId, Map<String, Object> model) {
		CreditCard creditCard = creditCardService.findCreditCardById(cardId);
		model.put("crediCard", creditCard);
		return VIEWS_CARD_DETAILS;
	}
}
