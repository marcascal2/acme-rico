package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.BankAccount;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.TransferApplication;
import org.springframework.samples.petclinic.service.BankAccountService;
import org.springframework.samples.petclinic.service.ClientService;
import org.springframework.samples.petclinic.service.TransferAppService;
import org.springframework.samples.petclinic.validator.TransferAppValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TransferAppController {

//	private static final String CREATE_APPLICATIONS_VIEW = "transfersApp/transfersAppCreate";
	private static final String EDIT_APPLICATIONS_VIEW = "transfersApp/transferAppDetails";
	private static final String LIST_APPLICATIONS_VIEW = "transfersApp/transferAppList";

	@Autowired
	private TransferAppService transferAppService;

	@Autowired
	private BankAccountService accountService;
	
	@Autowired
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
	@GetMapping(value = "/transferapps_mine/{clientId}")
	public String listMineTransfersApp(@PathVariable("clientId") int clientId,ModelMap modelMap) {
		Client client = this.clientService.findClientById(clientId);
		Collection<TransferApplication> transfers_app = 
			this.transferAppService.findAllTransfersApplicationsByClientId(client);
		modelMap.addAttribute("transfers_app", transfers_app);
		return LIST_APPLICATIONS_VIEW;
	}


	// Show
	@GetMapping(value = "/transferapps/{transferappsId}")
	public String showTransferApplication(@PathVariable("transferappsId") int transferappsId, ModelMap modelMap) {
		TransferApplication transferApp = this.transferAppService.findTransferAppById(transferappsId);
		modelMap.put("transfer_application", transferApp);
		return EDIT_APPLICATIONS_VIEW;
	}

	// Create
	@GetMapping(value = "/transferapps/{bank_account_id}/new")
	public String createTransfers(@PathVariable("bank_account_id") Integer accountId, Map<String, Object> model) {
		TransferApplication transfer_app = new TransferApplication();
		
		BankAccount account = this.accountService.findBankAccountById(accountId);
		Collection<TransferApplication> transferApps = account.getTransfersApps();
		transferApps.add(transfer_app);
		account.setTransfersApps(transferApps);
		Collection<TransferApplication> transferAppsC = account.getClient().getTransferApps();
		transferAppsC.add(transfer_app);
		account.getClient().setTransferApps(transferAppsC);

		transfer_app.setStatus("PENDING");

		model.put("transfer_app", transfer_app);

		return "transfersApp/transfersAppCreate";
	}

	@PostMapping(value = "/transferapps/{bank_account_id}/new")
	public String saveTransferApplication(@PathVariable("bank_account_id") Integer accountId, @ModelAttribute("transfer_app") @Valid TransferApplication transfer_app, BindingResult result, Map<String, Object> model) {

		BankAccount account = this.accountService.findBankAccountById(accountId);
		transfer_app.setBankAccount(account);
		transfer_app.setClient(account.getClient());
		
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
			this.transferAppService.checkInstant(transfer_app);
			return "redirect:/transferapps";
		}
	}

	// Accept application
	@GetMapping(value = "transferapps/{transferappsId}/accept/{bankAccountId}")
	public String acceptTransferApplication(@PathVariable("transferappsId") int transferappsId,
			@PathVariable("bankAccountId") int bankAccountId, ModelMap modelMap) {
				
		TransferApplication transferApplication = transferAppService.findTransferAppById(transferappsId);
		this.transferAppService.acceptApp(transferApplication);
		modelMap.addAttribute("transfer_application", transferApplication);

		return "redirect:/transferapps";
	}

	// Refuse application
	@GetMapping(value = "transferapps/{transferappsId}/refuse/{bankAccountId}")
	public String refuseTransferApplication(@PathVariable("transferappsId") int transferappsId,
			@PathVariable("bankAccountId") int bankAccountId, ModelMap modelMap) {
		TransferApplication transferApplication = transferAppService.findTransferAppById(transferappsId);
		
		this.transferAppService.refuseApp(transferApplication);
		modelMap.addAttribute("transfer_application", transferApplication);
		return "redirect:/transferapps";
	}

}
