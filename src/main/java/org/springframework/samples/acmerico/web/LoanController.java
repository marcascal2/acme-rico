package org.springframework.samples.acmerico.web;

import java.util.Collection;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.acmerico.model.Loan;
import org.springframework.samples.acmerico.service.LoanService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoanController {
	
	private final LoanService loanService;
	
	@Autowired
	public LoanController(LoanService loanService) {
		this.loanService = loanService;
	}
	
	@GetMapping(value = "/loans")
	public String listTransfersApp(ModelMap modelMap) {
		Collection<Loan> loans = this.loanService.findAllLoans();
		modelMap.addAttribute("loans", loans);
		return "loans/loanList";
	}
	
	@GetMapping(value = "/loans/new")
	public String initCreationForm(Map<String, Object> model) {
		Loan loan = new Loan();
		model.put("loan", loan);
		return "loans/createOrUpdateLoanForm";
	}

	@PostMapping(value = "/loans/new")
	public String processCreationForm(@Valid Loan loan, BindingResult result) {
		if (result.hasErrors()) {
			return "loans/createOrUpdateLoanForm";
		}
		else {
			this.loanService.save(loan);
			return "redirect:/loans";
		}
	}

	// Show
	@GetMapping(value = "/loans/{loanId}")
	public String showTransferApplication(@PathVariable("loanId") int loanId, ModelMap modelMap) {
		Loan loan = this.loanService.findLoanById(loanId);
		modelMap.put("loan", loan);
		return "loans/createOrUpdateLoanForm";
	}

}
