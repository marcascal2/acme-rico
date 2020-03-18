package org.springframework.samples.petclinic.web;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.BankAccount;
import org.springframework.samples.petclinic.model.TransferApplication;
import org.springframework.samples.petclinic.service.BankAccountService;
import org.springframework.samples.petclinic.service.TransferAppService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
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
		BankAccount account = this.accountService.findBankAccountById(accountId);
		transfer_app.setBankAccount(account);
		transfer_app.setClient(account.getClient());
		transfer_app.setStatus("PENDING");

		modelMap.addAttribute("transfer_app", transfer_app);

		return CREATE_APPLICATIONS_VIEW;
	}

	@PostMapping(value = "/transferapps/{bank_account_id}/new")
	public String saveTransferApplication(@PathVariable("bank_account_id") Integer accountId,
			@Valid TransferApplication transfer_app, BindingResult result, ModelMap modelMap) {
		
		
		BankAccount originBankAccount = this.accountService.findBankAccountById(accountId);
		transfer_app.setBankAccount(originBankAccount);
		transfer_app.setClient(originBankAccount.getClient());
		
		// Validations
		if (transfer_app.getAmount() > originBankAccount.getAmount()) {
			ObjectError obj = new ObjectError("amount", "This amount can´t be higher than bank account amount");
			result.addError(obj);
		}
		
		if (originBankAccount.getAccountNumber().equals(transfer_app.getAccount_number_destination())) {
			ObjectError obj = new ObjectError("checkSameAccount",
					"Account number can not be the same that destination number account");
			result.addError(obj);
		}
		
		
		// #######################################################################################

		if (result.hasErrors()) {
			result.getAllErrors().stream().forEach(x->System.out.println(x));
			modelMap.addAttribute("transfer_app", transfer_app);
			return CREATE_APPLICATIONS_VIEW;
		} else {
			this.transferAppService.checkInstant(transfer_app);
		}

		return LIST_APPLICATIONS_VIEW;
	}

}
