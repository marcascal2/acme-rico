package org.springframework.samples.petclinic.web;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Map;
import java.util.Random;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.BankAccount;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.CreditCard;
import org.springframework.samples.petclinic.service.BankAccountService;
import org.springframework.samples.petclinic.service.ClientService;
import org.springframework.samples.petclinic.service.CreditCardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CreditCardController {

	private static final String VIEWS_CARD_CREATE = "cards/new";
	private static final String VIEWS_CARD_DETAILS = "cards/show";
		
	private ClientService clientService;
	
	private CreditCardService creditCardService;

	@Autowired
	public CreditCardController(ClientService clientService, CreditCardService creditCardService) {
		this.clientService = clientService;
		this.creditCardService =  creditCardService;
	}
	
	@RequestMapping(value="/cards", method = RequestMethod.GET)
	  public String showClientCards(Principal principal, Model model) {
	      String username = principal.getName();
	      Client client = this.clientService.findClientByUserName(username);
	      Collection<CreditCard> result  = clientService.findCreditCardsByUsername(username);
	      model.addAttribute("cards", result);
	      model.addAttribute("clientId", client.getId());
	      return "cards/cards";
	  }
	  
		@GetMapping(value = "/cards/{clientId}/new")
		public String initCreationForm(@PathVariable("clientId") int clientId, Map<String, Object> model) {
			CreditCard card = new CreditCard();
			model.put("creditCard", card);
			return VIEWS_CARD_CREATE;
		}
	
		@PostMapping(value="/cards/{client_id}/new")
		public String processCreationForm(@PathVariable("client_id") Integer clientId, @Valid CreditCard creditCard, BindingResult result) {
				Client client = this.clientService.findClientById(clientId);
				DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MM-yyyy");
				LocalDate deadline = LocalDate.now().plusYears(2);
				String d = deadline.format(fmt);
				creditCard.setClient(client);
				creditCard.setDeadline(d);
				creditCard.setCvv("123");
				creditCard.setNumber("1234567890123456");
				try {
					this.creditCardService.saveCreditCard(creditCard);
					return "redirect:/cards/";
				} catch (Exception e) {
					System.out.println(e.getMessage());
					return VIEWS_CARD_CREATE;
				}
		}
		
		@GetMapping(value = "/cards/cardInfo/{cardId}")
		public String showAccountInfo(@PathVariable("cardId") int cardId, Map<String, Object> model) {
			CreditCard creditCard = creditCardService.findCreditCardById(cardId);
			model.put("crediCard", creditCard);
			return VIEWS_CARD_DETAILS;
		}
}
