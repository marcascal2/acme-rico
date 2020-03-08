package org.springframework.samples.petclinic.web;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Transfer;
import org.springframework.samples.petclinic.service.TransferService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/transfers")
public class TransferController {

	@Autowired
	private TransferService service;

	@GetMapping()
	public String listTransfers(ModelMap modelMap) {
		String view = "transfers/transfersList";
		Collection<Transfer> transfers = this.service.findAllTransfers();
		modelMap.addAttribute("transfers", transfers);
		return view;
	}

	@GetMapping(path = "/new")
	public String createTransfers(ModelMap modelMap) {
		String view = "transfers/editTransfers";
		modelMap.addAttribute("transfer", new Transfer());
		return view;
	}

	@PostMapping(path = "/save")
	public String saveTransfers(@Valid Transfer transfer, BindingResult result, ModelMap modelMap) {
		String view = "transfers/transfersList";
		if (result.hasErrors()) {
			modelMap.addAttribute("transfer", transfer);
			return "transfers/editTransfers";
		} else {
			service.save(transfer);
			modelMap.addAttribute("message", "Everything work well!!");
			view = listTransfers(modelMap);
		}

		return view;

	}

}