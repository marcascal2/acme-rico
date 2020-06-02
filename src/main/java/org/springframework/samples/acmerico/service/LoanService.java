package org.springframework.samples.acmerico.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
	@Cacheable("listLoans")
	public Collection<Loan> findAllLoans() {
		return (Collection<Loan>) this.loanRepository.findAll();
	}

	@Transactional
	@CacheEvict(cacheNames = "listLoans", allEntries = true)
	public void save(@Valid Loan loan) throws DataAccessException {
		this.loanRepository.save(loan);
	}

	@Transactional
	public Loan findLoanById(int loanId) {
		Optional<Loan> result = this.loanRepository.findById(loanId);
		if (result.isPresent()) {
			return result.get();
		} else {
			return null;
		}
	}

	public Boolean checkSingleLoan(int bankAccountId) {
		BankAccount account = this.accountService.findBankAccountById(bankAccountId);
		Boolean hasSingleLoan = account.getClient().getLoanApps().stream()
				.anyMatch(x -> x.getLoan().getSingle_loan().equals(true)
						&& (x.getStatus().equals("PENDING") || (x.getStatus().equals("ACCEPTED") && !x.isPaid())));

		if (hasSingleLoan) {
			return true;
		} else {
			return false;
		}
	}

	public List<LoanApplication> acceptedLoanApps(Loan loan) {
		return loan.getLoanApplications().stream().filter(la -> la.getStatus().equals("ACCEPTED"))
				.collect(Collectors.toList());
	}

}
