package org.springframework.samples.acmerico.web;

import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.acmerico.model.LoanApplication;
import org.springframework.samples.acmerico.service.LoanAppService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoanApplicationController{

    @Autowired
    private LoanAppService  loanAppService;

    @Autowired
	public LoanApplicationController(LoanAppService loanAppService) {
		this.loanAppService = loanAppService;
	}
	
	@GetMapping(value = "/loanapps")
	public String listLoanApp(ModelMap modelMap) {
		Collection<LoanApplication> loanApps = this.loanAppService.findAllLoanApps();
		modelMap.addAttribute("loanApps", loanApps);
		return "loanApp/loanAppList";
	}
    
    // @GetMapping(value = "/loanapps/new")
	// public String initCreationForm(Map<String, Object> model) {
	// 	LoanApplication loanApp = new LoanApplication();
	// 	model.put("loanApp", loanApp);
	// 	return "loanApp/createOrUpdateLoanApp";
	// }

	// @PostMapping(value = "/loanapps/new")
	// public String processCreationForm(@Valid LoanApplication loanApp, BindingResult result) {
	// 	if (result.hasErrors()) {
	// 		return "loanApp/createOrUpdateLoanApp";
	// 	}
	// 	else {
	// 		this.loanAppService.save(loanApp);
	// 		return "";
	// 	}
	// }


    

}