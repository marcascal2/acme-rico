package org.springframework.samples.petclinic.web;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.Transfer;
import org.springframework.samples.petclinic.model.TransferApplication;
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
	private TransferAppService service;

	// Listar
	@GetMapping(value= "/transferapps")
	public String listTransfersApp(ModelMap modelMap) {
		String view = "transfersApp/transferAppList";
		Collection<TransferApplication> transfers_app = this.service.findAllTransfersApplications();
		modelMap.addAttribute("transfers_app", transfers_app);
		return view;
	}

	// Update
	@GetMapping(value = "/transferapps/{transferappsId}/edit")
	public String initUpdateClientForm(@PathVariable("transferAppId") int transferAppId, Model model) {
		String view = "transfersApp/transfersAppEdit";
		TransferApplication transferApp = this.service.findTransferAppById(transferAppId);
		model.addAttribute(transferApp);
		return view;
	}

	@PostMapping(value = "/transferapps/{transferappsId}/edit")
	public String processUpdateClientForm(@Valid TransferApplication transferApp, BindingResult result,
			@PathVariable("transferAppId") int transferAppId) {
		String view = "transfersApp/transfersAppEdit";
		if (result.hasErrors()) {

			return view;
		} else {
			transferApp.setId(transferAppId);
			this.service.save(transferApp);
			return "redirect:/transferApp/{transferAppId}";
		}
	}
	
	@GetMapping(value = "/transferapps/{transferappsId}")
	public String showTransferApplication(@PathVariable("transferappsId") int transferappsId, ModelMap modelMap){ 
		try {
			System.out.println(transferappsId);
			TransferApplication transferApp = this.service.findTransferAppById(transferappsId);
			System.out.println(transferApp.getStatus());
			modelMap.put("transfer_application", transferApp);
			return "transfersApp/transferAppDetails";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "redirect:/";
		}
	}
}
