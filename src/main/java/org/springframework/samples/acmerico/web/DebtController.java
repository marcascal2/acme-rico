package org.springframework.samples.acmerico.web;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.acmerico.model.Debt;
import org.springframework.samples.acmerico.service.DebtService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DebtController {
	
	private static final String LIST_DEBTS_VIEW = "debts/debtsList";

	private DebtService debtService;
	
	@Autowired
	public DebtController(DebtService debtService) {
		this.debtService = debtService;
	}
	
	@GetMapping(value = "/debts/pending")
	public String listPendingDebts(ModelMap modelMap) {
		Collection<Debt> debts = this.debtService.getNonPayedDebts();
		modelMap.addAttribute("debts", debts);
		return LIST_DEBTS_VIEW;
	}

}
