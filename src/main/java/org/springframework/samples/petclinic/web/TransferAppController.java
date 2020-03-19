package org.springframework.samples.petclinic.web;

import java.util.Collection;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.BankAccount;
import org.springframework.samples.petclinic.model.TransferApplication;
import org.springframework.samples.petclinic.service.BankAccountService;
import org.springframework.samples.petclinic.service.TransferAppService;
import org.springframework.samples.petclinic.validator.TransferAppValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TransferAppController {

	private static final String CREATE_APPLICATIONS_VIEW = "transfersApp/transfersAppCreate";
	private static final String EDIT_APPLICATIONS_VIEW = "transfersApp/transferAppDetails";
	private static final String LIST_APPLICATIONS_VIEW = "transfersApp/transferAppList";

	@Autowired
	private TransferAppService transferAppService;

	@Autowired
	private BankAccountService accountService;
	
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

	// Show
	@GetMapping(value = "/transferapps/{transferappsId}")
	public String showTransferApplication(@PathVariable("transferappsId") int transferappsId, ModelMap modelMap) {
		TransferApplication transferApp = this.transferAppService.findTransferAppById(transferappsId);
		modelMap.put("transfer_application", transferApp);
		return EDIT_APPLICATIONS_VIEW;
	}

	// Create
	@GetMapping(value = "/transferapps/{bank_account_id}/new")
	public String createTransfers(@PathVariable("bank_account_id") Integer accountId, ModelMap modelMap) {
		TransferApplication transfer_app = new TransferApplication();
		
		transfer_app.setStatus("PENDING");

		modelMap.addAttribute("transfer_app", transfer_app);

		return CREATE_APPLICATIONS_VIEW;
	}

	@PostMapping(value = "/transferapps/{bank_account_id}/new")
	public String saveTransferApplication(@PathVariable("bank_account_id") Integer accountId,
			@Valid TransferApplication transfer_app, BindingResult result, ModelMap modelMap) {
		
		BankAccount account = this.accountService.findBankAccountById(accountId);
		Collection<TransferApplication> transferApps = account.getTransfersApps();
		transferApps.add(transfer_app);
		account.setTransfersApps(transferApps);
		Collection<TransferApplication> transferAppsC = account.getClient().getTransferApps();
		transferAppsC.add(transfer_app);
		account.getClient().setTransferApps(transferAppsC);
		
		transfer_app.setBankAccount(account);
		transfer_app.setClient(account.getClient());
		transfer_app.setStatus("PENDING");

		if (result.hasErrors()) {
			modelMap.addAttribute("transfer_app", transfer_app);
//			modelMap.put("errors", result.getAllErrors());
			return CREATE_APPLICATIONS_VIEW;
		} else {
			this.transferAppService.checkInstant(transfer_app);
		}

		return listTransfersApp(modelMap);
	}

	// Accept application
	@GetMapping(value = "transferapps/{transferappsId}/accept/{bankAccountId}")
	public String acceptTransferApplication(@PathVariable("transferappsId") int transferappsId,
			@PathVariable("bankAccountId") int bankAccountId, ModelMap modelMap) {
		TransferApplication transferApplication = transferAppService.findTransferAppById(transferappsId);
		BankAccount account = accountService.findBankAccountById(bankAccountId);

		transferApplication.setBankAccount(account);
		transferApplication.setClient(account.getClient());

		this.transferAppService.acceptApp(transferApplication);
		modelMap.addAttribute("transfer_application", transferApplication);

		return listTransfersApp(modelMap);
	}

	// Refuse application
	@GetMapping(value = "transferapps/{transferappsId}/refuse/{bankAccountId}")
	public String refuseTransferApplication(@PathVariable("transferappsId") int transferappsId,
			@PathVariable("bankAccountId") int bankAccountId, ModelMap modelMap) {
		TransferApplication transferApplication = transferAppService.findTransferAppById(transferappsId);
		BankAccount account = accountService.findBankAccountById(bankAccountId);

		transferApplication.setBankAccount(account);
		transferApplication.setClient(account.getClient());

		this.transferAppService.refuseApp(transferApplication);
		modelMap.addAttribute("transfer_application", transferApplication);
		return listTransfersApp(modelMap);
	}

}
