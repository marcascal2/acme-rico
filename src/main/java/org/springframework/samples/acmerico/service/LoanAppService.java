package org.springframework.samples.acmerico.service;

import java.time.LocalDate;
import java.util.Collection;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.acmerico.model.BankAccount;
import org.springframework.samples.acmerico.model.Client;
import org.springframework.samples.acmerico.model.Debt;
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
	private DebtService debtService;

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
		loanApp.setBankAccount(account);
		loanApp.setLoan(loan);
	}

	public void saveApplication(LoanApplication loanApp, Loan loan, Client client, BankAccount account) {
		loanApp.setClient(client);
		loanApp.setBankAccount(account);
		loanApp.setLoan(loan);
		loan.getLoanApplications().add(loanApp);
		this.loanService.save(loan);
		this.save(loanApp);
	}

	public Collection<LoanApplication> findLoanAppsByClient(int id) throws DataAccessException {
		Collection<LoanApplication> l_app = this.loanAppRepository.findAppByClientId(id);
		return l_app;
	}

	@Transactional
	public LoanApplication findLoanAppById(int loanAppId) {
		return this.loanAppRepository.findById(loanAppId).get();
	}

	@Transactional
	public void acceptLoanApp(LoanApplication loanApp) {
		BankAccount bankAccount = loanApp.getBankAccount();
		loanApp.setStatus("ACCEPTED");
		loanApp.setAmount_paid(loanApp.getLoan().getOpening_price());
		Double amount = bankAccount.getAmount();
		bankAccount.setAmount(amount + loanApp.getAmount() - loanApp.getLoan().getOpening_price());
		this.accountService.saveBankAccount(bankAccount);
		this.save(loanApp);
	}

	@Transactional
	public void refuseLoanApp(LoanApplication loanApp) {
		loanApp.setStatus("REJECTED");
		this.save(loanApp);
	}

	public Collection<LoanApplication> findLoanAppsAccepted() throws DataAccessException {
		Collection<LoanApplication> l_app = this.loanAppRepository.findLoanAppAccepted();
		return l_app;
	}

	@Transactional
	public void collectAcceptedLoans(Collection<LoanApplication> loanApps) {
		for (LoanApplication loanApp : loanApps) {
			if (!loanApp.isPaid()) {
				BankAccount account = loanApp.getBankAccount();
				if (account.getAmount() >= loanApp.getAmountToPay()) {
					if(loanApp.getPayedDeadlines() < loanApp.getLoan().getNumber_of_deadlines()) {
						Double amount = account.getAmount() - loanApp.getAmountToPay();
						account.setAmount(amount);
						this.accountService.saveBankAccount(account);
						Double amountPaid = loanApp.getAmount_paid() + loanApp.getAmountToPay();
						loanApp.setAmount_paid(amountPaid);
						loanApp.setPayedDeadlines(loanApp.getPayedDeadlines() + 1);
						this.save(loanApp);
					}
				} else {
					if(loanApp.getPayedDeadlines() < loanApp.getLoan().getNumber_of_deadlines()) {
						Debt debt = this.debtService.getClientDebt(loanApp.getClient());
						if(debt != null) {
							debt.setAmount(loanApp.getAmountToPay() + debt.getAmount());
							debt.setRefreshDate(this.refreshDate());
							loanApp.setPayedDeadlines(loanApp.getPayedDeadlines() + 1);
							this.save(loanApp);
							this.debtService.save(debt);
						} else {
							Debt newDebt = new Debt();
							newDebt.setAmount(loanApp.getAmountToPay());
							newDebt.setClient(loanApp.getClient());
							newDebt.setLoanApplication(loanApp);
							newDebt.setRefreshDate(this.refreshDate());
							loanApp.setPayedDeadlines(loanApp.getPayedDeadlines() + 1);
							this.save(loanApp);
							this.debtService.save(newDebt);
						}
					}
				}
			}
		}

	}

	private String refreshDate() {
		LocalDate now = LocalDate.now();
		int month = now.getMonthValue();
		String deadLine;

		if (month < 10) {
			deadLine = "0" + now.getMonthValue() + "/" + now.getYear();
		} else {
			deadLine = now.getMonthValue() + "/" + now.getYear();
		}
		return deadLine;
	}
}