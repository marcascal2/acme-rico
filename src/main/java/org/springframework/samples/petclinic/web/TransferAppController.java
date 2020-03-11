package org.springframework.samples.petclinic.web;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.BankAccount;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.Transfer;
import org.springframework.samples.petclinic.model.TransferApplication;
import org.springframework.samples.petclinic.service.BankAccountService;
import org.springframework.samples.petclinic.service.TransferAppService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TransferAppController {

	@Autowired
	private TransferAppService transferAppService;

	@Autowired
	private BankAccountService bankAccountService;

	// Listar
	@GetMapping(value = "/transferapps")
	public String listTransfersApp(ModelMap modelMap) {
		String view = "transfersApp/transferAppList";
		Collection<TransferApplication> transfers_app = this.transferAppService.findAllTransfersApplications();
		modelMap.addAttribute("transfers_app", transfers_app);
		return view;
	}

	// Show
	@GetMapping(value = "/transferapps/{transferappsId}")
	public String showTransferApplication(@PathVariable("transferappsId") int transferappsId, ModelMap modelMap) {
		try {
			System.out.println(transferappsId);
			TransferApplication transferApp = this.transferAppService.findTransferAppById(transferappsId);
			System.out.println(transferApp.getStatus());
			modelMap.put("transfer_application", transferApp);
			return "transfersApp/transferAppDetails";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "redirect:/";
		}
	}

	// Accept application
	@GetMapping(value = "transferapps/{transferappsId}/accept")
	public String acceptTransferApplication(@PathVariable("transferappsId") int transferappsId, ModelMap modelMap) {
		TransferApplication transferApplication = transferAppService.findTransferAppById(transferappsId);
		transferApplication.setStatus("ACCEPTED");
		Double transferAmount = transferApplication.getAmount();
		transferAppService.save(transferApplication);

		String accountOrigin = transferApplication.getAccount().getAccountNumber();
		BankAccount originAccount = this.bankAccountService.findBankAccountByNumber(accountOrigin);

		BankAccount destinationAccount = this.bankAccountService
				.findBankAccountByNumber(transferApplication.getAccount_number_destination());

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

		modelMap.addAttribute("transfer_application", transferApplication);
		return listTransfersApp(modelMap);
	}

	// Refuse application
	@GetMapping(value = "transferapps/{transferappsId}/refuse")
	public String refuseTransferApplication(@PathVariable("transferappsId") int transferappsId, ModelMap modelMap) {
		TransferApplication transferApplication = transferAppService.findTransferAppById(transferappsId);
		transferApplication.setStatus("REJECTED");
		transferAppService.save(transferApplication);
		modelMap.addAttribute("transfer_application", transferApplication);
		return listTransfersApp(modelMap);
	}

}
