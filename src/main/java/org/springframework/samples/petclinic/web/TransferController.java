package org.springframework.samples.petclinic.web;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.BankAccount;
import org.springframework.samples.petclinic.model.Transfer;
import org.springframework.samples.petclinic.model.TransferApplication;
import org.springframework.samples.petclinic.repository.BankAccountRepository;
import org.springframework.samples.petclinic.service.TransferAppService;
import org.springframework.samples.petclinic.service.TransferService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TransferController {

	@Autowired
	private TransferService transferService;

	@Autowired
	private BankAccountRepository accountRepository;

	@Autowired
	private TransferAppService transferAppService;

	@GetMapping("/transfers")
	public String listTransfers(ModelMap modelMap) {
		String view = "transfers/transfersList";
		Collection<Transfer> transfers = this.transferService.findAllTransfers();
		modelMap.addAttribute("transfers", transfers);
		return view;
	}

	@GetMapping(value = "/transfers/{bank_account_id}/new")
	public String createTransfers(@PathVariable("bank_account_id") Integer accountId, ModelMap modelMap) {
		String view = "transfers/editTransfers";
		Transfer transfer = new Transfer();
		BankAccount account = this.accountRepository.findById(accountId);
		String bankAccountNumber = account.getAccountNumber();
		transfer.setAccountNumber(bankAccountNumber);
		modelMap.addAttribute("transfer", transfer);
		return view;
	}

	@PostMapping(value = "/transfers/save")
	public String saveTransfers(@Valid Transfer transfer, BindingResult result, ModelMap modelMap) {
		String view = "transfers/transfersList";
//		if (transfer.getAmount() <= 100.00 || transfer.getAmount().equals(null)) {
//			ObjectError obj = new ObjectError("amount", "El amount debe ser mayor que 100.00");
//			result.addError(obj);
//		}
		if (result.hasErrors()) {
			modelMap.addAttribute("transfer", transfer);
			return "transfers/editTransfers";
		} else {

			String account_number_origin = transfer.getAccountNumber();
			BankAccount originAccount = this.accountRepository.findByAccountNumber(account_number_origin);

			TransferApplication tranfers_application = new TransferApplication();
			tranfers_application.setStatus("PENDING");
			tranfers_application.setAmount(transfer.getAmount());
			tranfers_application.setAccount_number_destination(transfer.getDestination());
			tranfers_application.setAccount(originAccount);
			transferAppService.save(tranfers_application);
			transferService.save(transfer);
			view = listTransfers(modelMap);
		}

		return view;

	}

}