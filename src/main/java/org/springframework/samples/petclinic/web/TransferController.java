package org.springframework.samples.petclinic.web;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.BankAccount;
import org.springframework.samples.petclinic.model.InstantTransfer;
import org.springframework.samples.petclinic.model.Transfer;
import org.springframework.samples.petclinic.model.TransferApplication;
import org.springframework.samples.petclinic.repository.BankAccountRepository;
import org.springframework.samples.petclinic.service.BankAccountService;
import org.springframework.samples.petclinic.service.InstantTransferService;
import org.springframework.samples.petclinic.service.TransferAppService;
import org.springframework.samples.petclinic.service.TransferService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TransferController {

	@Autowired
	private TransferService transferService;

	@Autowired
	private BankAccountRepository accountRepository;

	@Autowired
	private TransferAppService transferAppService;

	@Autowired
	private InstantTransferService insTransferService;

	@Autowired
	private BankAccountService bankAccountService;

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
		Double transferAmount = transfer.getAmount();
		String account_number_destination = transfer.getDestination();
		String account_number_origin = transfer.getAccountNumber();

		Double minAmount = 100.;

		BankAccount originAccount = this.accountRepository.findByAccountNumber(account_number_origin);
		BankAccount destinationAccount = this.accountRepository.findByAccountNumber(account_number_destination);

		Double originAmount = originAccount.getAmount();

		// Validations
		if (transferAmount > originAmount) {
			ObjectError obj = new ObjectError("amount", "This amount can´t be higher than bank account amount");
			result.addError(obj);
		}

		if (account_number_origin.equals(account_number_destination)) {
			ObjectError obj = new ObjectError("checkSameAccount",
					"Account number can not be the same that destination number account");
			result.addError(obj);
		}
		// ------------------------------------------------------------------------

		if (result.hasErrors()) {
			modelMap.addAttribute("transfer", transfer);
			return "transfers/editTransfers";
		} else {

			if (transferAmount < minAmount) {
				// Instant transfers
				InstantTransfer insTransfer = this.insTransferService.createInstantTrans(transferAmount,
						account_number_destination);

				this.insTransferService.save(insTransfer);

				if (destinationAccount != null) {
					// Descontamos el dinero a la cuenta origen y se lo añadimos al destino

					this.bankAccountService.sumAmount(transferAmount, destinationAccount);

					this.bankAccountService.SubstractAmount(transferAmount, originAccount);

					this.bankAccountService.saveBankAccount(originAccount);
					this.bankAccountService.saveBankAccount(destinationAccount);

				} else {
					// Solo le quitamos el dinero a la cuenta origen
					this.bankAccountService.SubstractAmount(transferAmount, originAccount);
					this.bankAccountService.saveBankAccount(originAccount);

				}

			} else {

				TransferApplication application = this.transferAppService.createTransferApp("PENDING", transferAmount,
						account_number_destination, originAccount);

				this.transferAppService.save(application);
				this.transferService.save(transfer);

			}

			view = listTransfers(modelMap);

		}

		return view;

	}

}