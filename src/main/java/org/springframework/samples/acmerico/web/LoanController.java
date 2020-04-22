package org.springframework.samples.acmerico.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.acmerico.model.BankAccount;
import org.springframework.samples.acmerico.model.Client;
import org.springframework.samples.acmerico.model.Loan;
import org.springframework.samples.acmerico.service.BankAccountService;
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

	private final BankAccountService accountService;
	
	@Autowired
	public LoanController(LoanService loanService, BankAccountService accountService) {
		this.loanService = loanService;
		this.accountService = accountService;
	}
	
	@GetMapping(value = "/loans")
	public String listTransfersApp(ModelMap modelMap) {
		Collection<Loan> loans = this.loanService.findAllLoans();
		modelMap.addAttribute("loans", loans);
		return "loans/loanList";
	}

	@GetMapping(value = "/loans/{bankAccountId}")
	public String listLoanForBankAccount(@PathVariable("bankAccountId") int bankAccountId, ModelMap modelMap) {
		Collection<Loan> loans = this.loanService.findAllLoans();
		BankAccount account = this.accountService.findBankAccountById(bankAccountId);
		Client c = account.getClient();
		double salaryPerMonth = c.getSalaryPerYear()/12;

		//Mostramos solo los loans disponibles para ese cliente, es decir, los cuales
		//su minimum_income es menor al salario anual
		Collection<Loan> loansForClient = loans.stream().filter(x->x.getMinimum_income() <= salaryPerMonth)
		.collect(Collectors.toList());

		modelMap.addAttribute("loans", loansForClient);
		modelMap.addAttribute("bankAccountId", bankAccountId);
		return "loans/personalicedLoanList";
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
	@GetMapping(value = "/loans/{bankAccountId}/{loanId}")
	public String showTransferApplication(@PathVariable("bankAccountId") int bankAccountId,
	@PathVariable("loanId") int loanId, ModelMap modelMap) {
		Loan loan = this.loanService.findLoanById(loanId);
		Boolean clienSingleLoan = this.loanService.checkSingleLoan(bankAccountId);

		modelMap.put("loan", loan);
		modelMap.put("bankAccountId", bankAccountId);
		modelMap.put("clienSingleLoan", clienSingleLoan);
		return "loans/loanInfo";
	}

}