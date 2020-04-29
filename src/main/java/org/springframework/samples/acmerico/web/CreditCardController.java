package org.springframework.samples.acmerico.web;

import java.security.Principal;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.acmerico.model.Client;
import org.springframework.samples.acmerico.model.CreditCard;
import org.springframework.samples.acmerico.service.ClientService;
import org.springframework.samples.acmerico.service.CreditCardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
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
	public String showCreditCardInfo(@PathVariable("cardId") int cardId, ModelMap model) {
		CreditCard creditCard = creditCardService.findCreditCardById(cardId);
		model.put("creditCard", creditCard);
		return VIEWS_CARD_DETAILS;
	}

	@GetMapping(value = "/cards/{cardId}/delete")
	public String deleteCreditCard(@PathVariable("cardId") int cardId) {
		try {
			this.creditCardService.deleteCreditCardById(cardId);
			return "redirect:/cards";
		} catch (Exception e) {
			return "redirect:/oups";
		}
	}
	
}
