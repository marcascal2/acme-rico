package org.springframework.samples.petclinic.web;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Transfer;
import org.springframework.samples.petclinic.model.TransferApplication;
import org.springframework.samples.petclinic.service.TransferAppService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/transferapps")
public class TransferAppContoller {
	
	@Autowired
	private TransferAppService	service;
	
	@GetMapping()
	public String listTransfersApp(ModelMap modelMap) {
		String view = "transfersApp/transferAppList";
		Collection<TransferApplication> transfers_app = this.service.findAllTransfersApplications();
		modelMap.addAttribute("transfers_app", transfers_app);
		return view;
	}

}
