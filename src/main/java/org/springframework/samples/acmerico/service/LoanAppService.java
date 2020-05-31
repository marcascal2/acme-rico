package org.springframework.samples.acmerico.service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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

	public void save(@Valid LoanApplication loanApp) {
		this.loanAppRepository.save(loanApp);
	}

	@Transactional(readOnly = true)
	public Collection<LoanApplication> findPendingsLoanApps() {
		return this.loanAppRepository.findPendings();
	}

	@Transactional(readOnly = true)
	public Collection<LoanApplication> findAcceptedLoanApps() {
		return this.loanAppRepository.findLoanAppAccepted();
	}

	public void setAttributes(int bankAccountId, int loanId, LoanApplication loanApp) {
		BankAccount account = this.accountService.findBankAccountById(bankAccountId);
		Loan loan = this.loanService.findLoanById(loanId);
		Client client = account.getClient();

		loanApp.setPayedDeadlines(0);
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
		loanApp.setPayedDeadlines(0);
		loan.getLoanApplications().add(loanApp);
		this.loanService.save(loan);
		this.save(loanApp);
	}

	public Collection<LoanApplication> findLoanAppsByClient(int id) {
		return this.loanAppRepository.findAppByClientId(id);
	}

	@Transactional
	public LoanApplication findLoanAppById(int loanAppId) {
		Optional<LoanApplication> result = this.loanAppRepository.findById(loanAppId);
		if (result.isPresent()) {
			return result.get();
		} else {
			return null;
		}
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

	public Collection<LoanApplication> findLoanAppsAccepted() {
		Collection<LoanApplication> l_app = this.loanAppRepository.findLoanAppAccepted();
		return l_app;
	}
	
	private Boolean accountHasEnoughtMoney(BankAccount account, LoanApplication loanApp) {
		return account.getAmount() >= loanApp.getAmountToPay();
	}
	
	private Boolean areRemainingDeadlines(LoanApplication loanApp) {
		return loanApp.getPayedDeadlines() < loanApp.getLoan().getNumber_of_deadlines();
	}
	
	private void collectLoanApp(BankAccount account, LoanApplication loanApp) {
		Double amount = account.getAmount() - loanApp.getAmountToPay();
		amount = (double) Math.round(amount * 100) / 100;
		account.setAmount(amount);
		this.accountService.saveBankAccount(account);
		Double amountPaid = loanApp.getAmount_paid() + loanApp.getAmountToPay();
		amountPaid = (double) Math.round(amountPaid * 100) / 100;
		loanApp.setAmount_paid(amountPaid);
		loanApp.setPayedDeadlines(loanApp.getPayedDeadlines() + 1);
		this.save(loanApp);
	}
	
	private void updateDebtAmount(Debt debt, LoanApplication loanApp) {
		debt.setAmount(loanApp.getAmountToPay() + debt.getAmount());
		debt.setRefreshDate(this.refreshDate());
		loanApp.setPayedDeadlines(loanApp.getPayedDeadlines() + 1);
		this.save(loanApp);
		this.debtService.save(debt);
	}
	
	private void registerNewDebt(LoanApplication loanApp) {
		Debt newDebt = new Debt();
		newDebt.setAmount(loanApp.getAmountToPay());
		newDebt.setClient(loanApp.getClient());
		newDebt.setLoanApplication(loanApp);
		newDebt.setRefreshDate(this.refreshDate());
		loanApp.setPayedDeadlines(loanApp.getPayedDeadlines() + 1);
		this.save(loanApp);
		this.debtService.save(newDebt);
	}
	
	private void manageDebt(LoanApplication loanApp) {
		Debt debt = this.debtService.getClientDebt(loanApp.getClient());
		if (debt != null) {
			updateDebtAmount(debt, loanApp);
		} else {
			registerNewDebt(loanApp);
		}
	}

	@Transactional
	public void collectAcceptedLoans(Collection<LoanApplication> loanApps) {
		for (LoanApplication loanApp : loanApps) {
			if (!loanApp.isPaid()) {
				BankAccount account = loanApp.getBankAccount();
				if (accountHasEnoughtMoney(account, loanApp)) {
					if (areRemainingDeadlines(loanApp)) {
						collectLoanApp(account, loanApp);
					}
				} else {
					if (areRemainingDeadlines(loanApp)) {
						manageDebt(loanApp);
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