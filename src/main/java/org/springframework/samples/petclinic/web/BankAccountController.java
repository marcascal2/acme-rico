package org.springframework.samples.petclinic.web;

import static org.junit.Assert.assertFalse;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.BankAccount;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.service.BankAccountService;
import org.springframework.samples.petclinic.service.ClientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class BankAccountController {

	private static final String VIEWS_ACCOUNT_CREATE = "accounts/createAccountForm";
	private static final String VIEWS_ACCOUNT_DETAILS = "accounts/showAccountInfo";
	private static final String VIEWS_ACCOUNT_DEPOSIT_MONEY = "accounts/depositMoney";

	private final ClientService clientService;

	private final BankAccountService bankAccountService;

	@Autowired
	public BankAccountController(ClientService clientService, BankAccountService bankAccountService) {
		this.clientService = clientService;
		this.bankAccountService = bankAccountService;
	}

	@RequestMapping(value = "/accounts", method = RequestMethod.GET)
	public String showClientAccounts(Principal principal, Model model) {
		String username = principal.getName();
		Client client = this.clientService.findClientByUserName(username);
		Collection<BankAccount> result = clientService.findBankAccountsByUsername(username);
		model.addAttribute("accounts", result);
		model.addAttribute("clientId", client.getId());
		return "accounts/accounts";
	}

	@GetMapping(value = "/accounts/{clientId}/new")
	public String initCreationForm(@PathVariable("clientId") int clientId, Map<String, Object> model) {
		BankAccount bankAccount = new BankAccount();
		model.put("bankAccount", bankAccount);
		return VIEWS_ACCOUNT_CREATE;
	}

	@PostMapping(value = "/accounts/{client_id}/new")
	public String processCreationForm(@PathVariable("client_id") Integer clientId, @Valid BankAccount bankAccount,
			BindingResult result) {
		Client client = this.clientService.findClientById(clientId);
		bankAccount.setClient(client);
		bankAccount.setCreatedAt(LocalDateTime.now().minusSeconds(2));
		try {
			this.bankAccountService.saveBankAccount(bankAccount);
			return "redirect:/accounts/";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return VIEWS_ACCOUNT_CREATE;
		}
	}

	@GetMapping(value = "/accounts/{accountId}")
	public String showAccountInfo(@PathVariable("accountId") int accountId, Map<String, Object> model) {
		BankAccount bankAccount = bankAccountService.findBankAccountById(accountId);
		Double money = bankAccount.getAmount();
		Boolean noMoney = money == 0. ? true : false;
		model.put("noMoney", noMoney);
		model.put("bankAccount", bankAccount);
		return VIEWS_ACCOUNT_DETAILS;
	}

	@GetMapping(value = "/accounts/{accountId}/delete")
	public String deleteAccount(@PathVariable("accountId") int accountId, Model model) {
		BankAccount bankAccount = bankAccountService.findBankAccountById(accountId);
		try {
			this.bankAccountService.deleteAccount(bankAccount);
			return "redirect:/accounts/";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return VIEWS_ACCOUNT_DETAILS;
		}
	}

	@GetMapping(value = "/accounts/{accountId}/depositMoney")
	public String depositMoney(@PathVariable("accountId") int accountId, Map<String, Object> model) {
		BankAccount bankAccount = this.bankAccountService.findBankAccountById(accountId);
		model.put("bankAccount", bankAccount);
		return VIEWS_ACCOUNT_DEPOSIT_MONEY;
	}

	@PostMapping(value = "/accounts/{accountId}/depositMoney")
	public String makeDeposit(@PathVariable("accountId") Integer accountId, @Valid BankAccount bankAccount,
			BindingResult result) {
		BankAccount account = this.bankAccountService.findBankAccountById(accountId);
		Double oldAmount = account.getAmount();
		String deposit = (String) result.getFieldValue("amount");
		Double d = Double.valueOf(deposit);
		assertFalse(d.compareTo(0.) < 0);
		account.setAmount(oldAmount + d);
		try {
			this.bankAccountService.saveBankAccount(account);
			return "redirect:/accounts/";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return VIEWS_ACCOUNT_DEPOSIT_MONEY;
		}
	}
}
