package org.springframework.samples.acmerico.web;

import java.security.Principal;
import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.acmerico.model.BankAccount;
import org.springframework.samples.acmerico.model.Client;
import org.springframework.samples.acmerico.model.Loan;
import org.springframework.samples.acmerico.model.LoanApplication;
import org.springframework.samples.acmerico.service.BankAccountService;
import org.springframework.samples.acmerico.service.ClientService;
import org.springframework.samples.acmerico.service.LoanAppService;
import org.springframework.samples.acmerico.service.LoanService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoanApplicationController {

	private final ClientService clientService;

	private LoanAppService loanAppService;

	private BankAccountService accountService;

	private LoanService loanService;

	@Autowired
	public LoanApplicationController(LoanAppService loanAppService, BankAccountService accountService,
			LoanService loanService, ClientService clientService) {
		this.loanAppService = loanAppService;
		this.accountService = accountService;
		this.loanService = loanService;
		this.clientService = clientService;
	}

	@GetMapping(value = "/loanapps")
	public String listLoanApp(ModelMap modelMap) {
		Collection<LoanApplication> loanApps = this.loanAppService.findPendingsLoanApps();
		Collection<LoanApplication> loanAppsAccepted = this.loanAppService.findAcceptedLoanApps();
		modelMap.addAttribute("loanApps", loanApps);
		modelMap.addAttribute("loanAppsAccepted", loanAppsAccepted);
		return "loanApp/loanAppList";
	}

	@GetMapping(value = "/myloanapps")
	public String listClientLoanApp(Principal principal, Model model) {
		String username = principal.getName();
		Client client = this.clientService.findClientByUserName(username);
		Collection<LoanApplication> loanApps = this.loanAppService.findLoanAppsByClient(client.getId());
		model.addAttribute("loanApps", loanApps);
		model.addAttribute("clientUser", client.getUser().getUsername());
		return "loanApp/clientLoanApps";
	}

	@GetMapping(value = "/loanapps/{loanId}/new/{bankAccountId}")
	public String initCreationForm(@PathVariable("bankAccountId") int bankAccountId, @PathVariable("loanId") int loanId,
			Map<String, Object> model) {
		LoanApplication loanApp = new LoanApplication();
		this.loanAppService.setAttributes(bankAccountId, loanId, loanApp);
		model.put("loan_app", loanApp);
		return "loanApp/createOrUpdateLoanApp";
	}

	@PostMapping(value = "/loanapps/{loanId}/new/{bankAccountId}")
	public String processCreationForm(@PathVariable("bankAccountId") int bankAccountId,
			@PathVariable("loanId") int loanId, @ModelAttribute("loan_app") LoanApplication loanApp,
			BindingResult result, ModelMap modelMap) {

		BankAccount account = this.accountService.findBankAccountById(bankAccountId);
		Loan loan = this.loanService.findLoanById(loanId);
		Client client = account.getClient();

		if (loanApp.getPurpose().isEmpty() || loanApp.getPurpose()==null) {
			result.rejectValue("purpose", "This purpuse can´t be empty", "This purpose can´t be empty");

		}

		if (loanApp.getAmount() == null) {
			result.rejectValue("amount", "Amount can´t be empty", "Amount can´t be empty");
		}

		if (loanApp.getAmount() != null) {
			if ((loan.getMinimum_amount() < loanApp.getAmount())) {
				result.rejectValue("amount", "This amount can´t be higher than loan amount",
						"This amount can´t be higher than loan amount");
			}

			if (loanApp.getAmount() < 100.0) {
				result.rejectValue("amount", "This amount can´t be lower than minimum amount",
						"This amount can´t be lower than minimum amount");

			}

			if (loanApp.getAmount() > 1000000.00) {
				result.rejectValue("amount", "This amount can´t be bigger than minimum amount",
						"This amount can´t be bigger than minimum amount");

			}
		}

		if (result.hasErrors()) {
			return "loanApp/createOrUpdateLoanApp";
		} else {

			this.loanAppService.saveApplication(loanApp, loan, client, account);
			return "redirect:/myloanapps";
		}
	}

	@GetMapping(value = "/loanapps/{loanappsId}")
	public String showLoanApplication(@PathVariable("loanappsId") int loanappsId, ModelMap modelMap) {
		try {
			LoanApplication loanApp = this.loanAppService.findLoanAppById(loanappsId);
			Integer remaining_deadlines = loanApp.getLoan().getNumber_of_deadlines() - loanApp.getPayedDeadlines();
			modelMap.put("loan_app", loanApp);
			modelMap.put("isPaid", loanApp.isPaid());
			modelMap.put("remaining_deadlines", remaining_deadlines);
			return "loanApp/loanAppDetails";
		} catch (Exception e) {
			return "redirect:/";
		}
	}

	@GetMapping(value = "/loanapps/{loanappsId}/accept")
	public String acceptLoanApplication(@PathVariable("loanappsId") int loanappsId, ModelMap modelMap) {
		LoanApplication loanApplication = this.loanAppService.findLoanAppById(loanappsId);
		this.loanAppService.acceptLoanApp(loanApplication);

		return "redirect:/loanapps";
	}

	@GetMapping(value = "/loanapps/{loanappsId}/refuse")
	public String refuseLoanApplication(@PathVariable("loanappsId") int loanappsId, ModelMap modelMap) {
		LoanApplication loanApplication = this.loanAppService.findLoanAppById(loanappsId);
		this.loanAppService.refuseLoanApp(loanApplication);

		return "redirect:/loanapps";
	}
	
	@GetMapping(value = "/loanapps/collect")
	public String collectAcceptedLoans(ModelMap modelMap) {
		Collection<LoanApplication> acceptedLoanAplications = this.loanAppService.findLoanAppsAccepted();
		this.loanAppService.collectAcceptedLoans(acceptedLoanAplications);
		
		return "redirect:/loanapps";
	}

}