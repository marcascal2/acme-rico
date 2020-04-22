package org.springframework.samples.acmerico.service;

import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.acmerico.model.BankAccount;
import org.springframework.samples.acmerico.model.Client;
import org.springframework.samples.acmerico.model.Loan;
import org.springframework.samples.acmerico.model.LoanApplication;
import org.springframework.samples.acmerico.repository.LoanApplicationRepository;
import org.springframework.stereotype.Service;

@Service
public class LoanAppService {

    @Autowired
    private LoanApplicationRepository loanAppRepository;

    @Autowired
    private BankAccountService accountService;
  
    @Autowired
    private LoanService loanService;

    @Autowired
	public LoanAppService(LoanApplicationRepository loanAppRepository) {
		this.loanAppRepository = loanAppRepository;
    }
    
    @Transactional
	public void save(@Valid LoanApplication loanApp) throws DataAccessException {
		this.loanAppRepository.save(loanApp);
    }
    
    @Transactional
	public Collection<LoanApplication> findAllLoanApps() {
		return (Collection<LoanApplication>) this.loanAppRepository.findAll();
	}

	public void setAttributes(int bankAccountId, int loanId, LoanApplication loanApp) {
    BankAccount account = this.accountService.findBankAccountById(bankAccountId);
		Loan loan = this.loanService.findLoanById(loanId);
		Client client = account.getClient();
	
		loanApp.setStatus("PENDING");
		loanApp.setAmount_paid(0.0);
		loanApp.setClient(client);
		loanApp.setDestination(account);
		loanApp.setLoan(loan);
	}

}