package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.BankAccount;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.CreditCardApplication;
import org.springframework.samples.petclinic.service.BankAccountService;
import org.springframework.samples.petclinic.service.CreditCardAppService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CreditCardAppController {
	
//	private static String creditCardApplicationSuccessfullyCreated = "creditCardApp/successfullyCreated";

	@Autowired
	private CreditCardAppService creditCardAppService;
	
	@Autowired
	private BankAccountService	bankAccountService;
	
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
		return "creditCardApp/successfullyCreated";
	}
}
