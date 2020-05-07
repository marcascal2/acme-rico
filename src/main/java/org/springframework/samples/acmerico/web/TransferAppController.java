package org.springframework.samples.acmerico.web;

import java.security.Principal;
import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.acmerico.model.BankAccount;
import org.springframework.samples.acmerico.model.Client;
import org.springframework.samples.acmerico.model.TransferApplication;
import org.springframework.samples.acmerico.service.BankAccountService;
import org.springframework.samples.acmerico.service.ClientService;
import org.springframework.samples.acmerico.service.TransferAppService;
import org.springframework.samples.acmerico.validator.TransferAppValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TransferAppController {

	private static final String EDIT_APPLICATIONS_VIEW = "transfersApp/transferAppDetails";
	private static final String LIST_APPLICATIONS_VIEW = "transfersApp/transferAppList";

	private TransferAppService transferAppService;

	private BankAccountService accountService;
	
	private ClientService clientService;
	
	@Autowired
	public TransferAppController(TransferAppService transferAppService, BankAccountService accountService, ClientService clientService) {
		this.transferAppService = transferAppService;
		this.accountService = accountService;
		this.clientService = clientService;
	}
	
	@InitBinder("transfer_app")
	public void initTransferAppBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new TransferAppValidator());
	}

	// Listar
	@GetMapping(value = "/transferapps")
	public String listTransfersApp(ModelMap modelMap) {
		Collection<TransferApplication> transfers_app = this.transferAppService.findAllTransfersApplications();
		modelMap.addAttribute("transfers_app", transfers_app);
		return LIST_APPLICATIONS_VIEW;
	}

	// Listar transferAplications propias
	@RequestMapping(value = "/transferapps_mine")
	public String listMineTransfersApp(Principal principal,ModelMap modelMap) {
		String username = principal.getName();
		Client client = this.clientService.findClientByUserName(username);
		Collection<TransferApplication> transfers_app = 
			this.transferAppService.findAllTransfersApplicationsByClient(client);
		modelMap.addAttribute("transfers_app", transfers_app);
		return LIST_APPLICATIONS_VIEW;
	}


	// Show
	@GetMapping(value = "/transferapps/{transferappsId}")
	public String showTransferApplication(@PathVariable("transferappsId") int transferappsId, ModelMap modelMap) {
		TransferApplication transferApp = this.transferAppService.findTransferAppById(transferappsId);
		
		boolean accountHasMoney = transferApp.getBankAccount().getAmount() >= transferApp.getAmount();
		modelMap.put("transfer_application", transferApp);
		modelMap.put("accountHasMoney", accountHasMoney);
		return EDIT_APPLICATIONS_VIEW;
	}

	// Create
	@GetMapping(value = "/transferapps/{bank_account_id}/new")
	public String createTransfers(@PathVariable("bank_account_id") Integer accountId, Map<String, Object> model) {
		TransferApplication transfer_app = new TransferApplication();

		transfer_app.setStatus("PENDING");
		model.put("transfer_app", transfer_app);

		return "transfersApp/transfersAppCreate";
	}

	@PostMapping(value = "/transferapps/{bank_account_id}/new")
	public String saveTransferApplication(@PathVariable("bank_account_id") Integer accountId, @ModelAttribute("transfer_app") @Valid TransferApplication transfer_app, BindingResult result, Map<String, Object> model) {
		BankAccount account = this.accountService.findBankAccountById(accountId);
		
		if(transfer_app.getAmount()!=null){
			if (transfer_app.getAmount() > account.getAmount()) {
				result.rejectValue("amount", "This amount can´t be higher than bank account amount", "This amount can´t be higher than bank account amount");
			}
		}

		if (account.getAccountNumber().equals(transfer_app.getAccount_number_destination())) {
			result.rejectValue("account_number_destination", "Account number can not be the same that destination number account", "Account number can not be the same that destination number account");
		}
		

		if (result.hasErrors()) {
			model.put("transfer_app", transfer_app);
			return "transfersApp/transfersAppCreate";
		} else {
			transfer_app.setBankAccount(account);
			transfer_app.setClient(account.getClient());
			this.transferAppService.checkInstant(transfer_app);
			return "redirect:/accounts";
		}
	}

	// Accept application
	@GetMapping(value = "/transferapps/{transferappsId}/accept/{bankAccountId}")
	public String acceptTransferApplication(@PathVariable("transferappsId") int transferappsId,
			@PathVariable("bankAccountId") int bankAccountId, ModelMap modelMap) {
		TransferApplication transferApplication = this.transferAppService.findTransferAppById(transferappsId);
		this.transferAppService.acceptApp(transferApplication);

		return "redirect:/transferapps";
	}

	// Refuse application
	@GetMapping(value = "/transferapps/{transferappsId}/refuse/{bankAccountId}")
	public String refuseTransferApplication(@PathVariable("transferappsId") int transferappsId,
			@PathVariable("bankAccountId") int bankAccountId, ModelMap modelMap) {
		TransferApplication transferApplication = this.transferAppService.findTransferAppById(transferappsId);
		this.transferAppService.refuseApp(transferApplication);
		
		return "redirect:/transferapps";
	}
}
