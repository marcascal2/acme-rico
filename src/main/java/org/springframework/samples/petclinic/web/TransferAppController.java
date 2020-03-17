package org.springframework.samples.petclinic.web;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.TransferApplication;
import org.springframework.samples.petclinic.service.TransferAppService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class TransferAppController {

	@Autowired
	private TransferAppService transferAppService;

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
		this.transferAppService.acceptApp(transferApplication);

		modelMap.addAttribute("transfer_application", transferApplication);
		return listTransfersApp(modelMap);
	}

	// Refuse application
	@GetMapping(value = "transferapps/{transferappsId}/refuse")
	public String refuseTransferApplication(@PathVariable("transferappsId") int transferappsId, ModelMap modelMap) {
		TransferApplication transferApplication = transferAppService.findTransferAppById(transferappsId);
		this.transferAppService.refuseApp(transferApplication);

		modelMap.addAttribute("transfer_application", transferApplication);
		return listTransfersApp(modelMap);
	}

}
