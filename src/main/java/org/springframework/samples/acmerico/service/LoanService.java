package org.springframework.samples.acmerico.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.acmerico.model.BankAccount;
import org.springframework.samples.acmerico.model.Loan;
import org.springframework.samples.acmerico.model.LoanApplication;
import org.springframework.samples.acmerico.repository.LoanRepository;
import org.springframework.stereotype.Service;

@Service
public class LoanService {
	
	@Autowired
	private LoanRepository loanRepository;

	@Autowired
	private BankAccountService accountService;
	
	@Autowired
	public LoanService(LoanRepository loanRepository) {
		this.loanRepository = loanRepository;
	}
	
	@Transactional
	public Collection<Loan> findAllLoans() {
		return (Collection<Loan>) this.loanRepository.findAll();
	}

 	@Transactional
	public void save(@Valid Loan loan) throws DataAccessException {
		this.loanRepository.save(loan);
	}

	@Transactional
	public Loan findLoanById(int loanId) {
		return loanRepository.findById(loanId).get();
	}

	public Boolean checkSingleLoan(int bankAccountId) {
		BankAccount account = this.accountService.findBankAccountById(bankAccountId);
		Boolean hasSingleLoan = account.getClient().getLoanApps().stream().anyMatch(x->x.getLoan().getSingle_loan().equals(true) && (x.getStatus().equals("PENDING") || (x.getStatus().equals("ACCEPTED") && !x.isPaid())));

		if(hasSingleLoan){
			return true;
		}else{
			return false;
		}		
	}

	public List<LoanApplication> acceptedLoanApps(Loan loan) {
		return loan.getLoanApplications().stream().filter(la->la.getStatus().equals("ACCEPTED")).collect(Collectors.toList());
	}

}
