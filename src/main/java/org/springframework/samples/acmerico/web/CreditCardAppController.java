package org.springframework.samples.acmerico.web;

import java.security.Principal;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.acmerico.model.BankAccount;
import org.springframework.samples.acmerico.model.Client;
import org.springframework.samples.acmerico.model.CreditCardApplication;
import org.springframework.samples.acmerico.service.BankAccountService;
import org.springframework.samples.acmerico.service.ClientService;
import org.springframework.samples.acmerico.service.CreditCardAppService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CreditCardAppController {

	private final ClientService clientService;

	private final CreditCardAppService creditCardAppService;
  
  @Autowired
	private BankAccountService	bankAccountService;

	@Autowired
	public CreditCardAppController(ClientService clientService, CreditCardAppService creditCardAppService) {
		this.clientService = clientService;
		this.creditCardAppService = creditCardAppService;
	}
	
	@GetMapping(value= "/creditcardapps")
	public String listCreditCardApps(ModelMap modelMap) {
		String view = "creditCardApps/creditCardAppList";
		Collection<CreditCardApplication> credit_cards_app = this.creditCardAppService.findCreditCardApps();
		modelMap.addAttribute("credit_cards_app", credit_cards_app);
		return view;
	}
	
	@GetMapping(value = "/creditcardapps/{creditcardappsId}")
	public String showCreditCardApplication(@PathVariable("creditcardappsId") int creditcardappsId, ModelMap modelMap){ 
		try {
			CreditCardApplication creditCardApp = this.creditCardAppService.findCreditCardAppById(creditcardappsId);
			modelMap.put("credit_card_app", creditCardApp);
			return "creditCardApps/creditCardAppDetails";
		} catch (Exception e) {
			return "redirect:/";
		}
	}
	
	@GetMapping(value = "creditcardapps/{creditcardappsId}/accept")
	public String acceptCreditCardApplication(@PathVariable("creditcardappsId") int creditcardappsId, ModelMap modelMap) {
		CreditCardApplication transferApplication = this.creditCardAppService.findCreditCardAppById(creditcardappsId);
		this.creditCardAppService.acceptApp(transferApplication);

		modelMap.addAttribute("transfer_application", transferApplication);
		return listCreditCardApps(modelMap);
	}

	@GetMapping(value = "creditcardapps/{creditcardappsId}/refuse")
	public String refuseCreditCardApplication(@PathVariable("creditcardappsId") int creditcardappsId, ModelMap modelMap) {
		CreditCardApplication transferApplication = this.creditCardAppService.findCreditCardAppById(creditcardappsId);
		this.creditCardAppService.refuseApp(transferApplication);

		modelMap.addAttribute("transfer_application", transferApplication);
		return listCreditCardApps(modelMap);
	}
	
	@RequestMapping(value = "/mycreditcardapps", method = RequestMethod.GET)
	public String showClientCardApps(Principal principal, Model model) {
		String view = "creditCardApps/clientCreditCardApps";
		String username = principal.getName();
		Client client = this.clientService.findClientByUserName(username);
		Collection<CreditCardApplication> result = creditCardAppService.findCreditCardAppByClientId(client.getId());
		model.addAttribute("cardApps", result);
		model.addAttribute("clientUser", client.getUser().getUsername());
		return view;
	}
  
  @GetMapping(value = "/creditcardapps/{bankAccountId}/new")
	public String requestNewCreditCard(Model model, @PathVariable("bankAccountId") int bankAccountId) {
		try {
			CreditCardApplication creditCardApp = new CreditCardApplication();
			creditCardApp.setStatus("PENDING");
			BankAccount bankAccount = this.bankAccountService.findBankAccountById(bankAccountId);
			creditCardApp.setBankAccount(bankAccount);
			Client client = bankAccount.getClient();
			creditCardApp.setClient(client);
			this.creditCardAppService.save(creditCardApp);
			return "redirect:/creditcardapps/created";
		} catch (Exception e) {
			return "redirect:/oups";
		}
	}
	
	@GetMapping(value = "/creditcardapps/created")
	public String creditCarddAppCreated(Model model) {
		return "creditCardApps/successfullyCreated";
	}
	
}
