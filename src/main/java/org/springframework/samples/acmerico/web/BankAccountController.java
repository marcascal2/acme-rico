package org.springframework.samples.acmerico.web;

import static org.junit.Assert.assertFalse;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.acmerico.model.BankAccount;
import org.springframework.samples.acmerico.model.Client;
import org.springframework.samples.acmerico.service.BankAccountService;
import org.springframework.samples.acmerico.service.ClientService;
import org.springframework.samples.acmerico.util.BankAccountNumberGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BankAccountController {

	private static final String VIEWS_ACCOUNT_CREATE = "accounts/createAccountForm";
	private static final String VIEWS_ACCOUNT_DETAILS = "accounts/showAccountInfo";
	private static final String VIEWS_ACCOUNT_DEPOSIT_MONEY = "accounts/depositMoney";
	private static final String VIEWS_REDIRECT_ACCOUNTS = "redirect:/accounts/";
	private static final String BANK_ACCOUNT = "bankAccount";

	private final ClientService clientService;

	private final BankAccountService bankAccountService;

	@Autowired
	public BankAccountController(ClientService clientService, BankAccountService bankAccountService) {
		this.clientService = clientService;
		this.bankAccountService = bankAccountService;
	}

	@GetMapping(value = "/accounts")
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
		BankAccountNumberGenerator generator = new BankAccountNumberGenerator();
		String newAccountNumber = generator.generateRandomNumber();
		while(Boolean.TRUE.equals(this.bankAccountService.accountNumberAlreadyUsed(newAccountNumber))) {
			newAccountNumber = generator.generateRandomNumber();
		}
		BankAccount bankAccount = new BankAccount();
		bankAccount.setAccountNumber(newAccountNumber);
		model.put(BANK_ACCOUNT, bankAccount);
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
			return VIEWS_REDIRECT_ACCOUNTS;
		} catch (Exception e) {
			return VIEWS_ACCOUNT_CREATE;
		}
	}

	@GetMapping(value = "/accounts/{accountId}")
	public String showAccountInfo(@PathVariable("accountId") int accountId, Map<String, Object> model) {
		BankAccount bankAccount = bankAccountService.findBankAccountById(accountId);
		Double money = bankAccount.getAmount();
		Boolean noMoney = money == 0.;
		model.put("noMoney", noMoney);
		model.put(BANK_ACCOUNT, bankAccount);
		return VIEWS_ACCOUNT_DETAILS;
	}

	@GetMapping(value = "/accounts/{accountId}/depositMoney")
	public String depositMoney(@PathVariable("accountId") int accountId, Map<String, Object> model) {
		BankAccount bankAccount = this.bankAccountService.findBankAccountById(accountId);
		model.put(BANK_ACCOUNT, bankAccount);
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
			return VIEWS_REDIRECT_ACCOUNTS;
		} catch (Exception e) {
			return VIEWS_ACCOUNT_DEPOSIT_MONEY;
		}
	}
}
