package org.springframework.samples.acmerico.web;

import java.security.Principal;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.acmerico.model.Client;
import org.springframework.samples.acmerico.model.CreditCard;
import org.springframework.samples.acmerico.service.ClientService;
import org.springframework.samples.acmerico.service.CreditCardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;


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
	
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	@GetMapping(value = "/cards")
	public String showClientCards(Principal principal, Model model) {
		Client client = this.clientService.findClientByUserName(principal.getName());
		Collection<CreditCard> result = clientService.findCreditCardsByUsername(principal.getName());
		model.addAttribute("cards", result);
		model.addAttribute("clientId", client.getId());
		return "cards/cards";
	}

	@GetMapping(value = "/cards/{cardId}/show")
	public String showAccountInfo(@PathVariable("cardId") int cardId, Model model) {
		CreditCard creditCard = creditCardService.findCreditCardById(cardId);
		model.addAttribute("creditCard", creditCard);
		return VIEWS_CARD_DETAILS;
	}

	
	@GetMapping("/cards/{creditCardId}/delete")
	public String deleteCard(@PathVariable("creditCardId") int cardId, Model model) {
		CreditCard cc = this.creditCardService.findCreditCardById(cardId);
		
		this.creditCardService.deleteCard(cc);
		return "cards/cards";
	}
	
}
