package org.springframework.samples.petclinic.service;

import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.BankAccount;
import org.springframework.samples.petclinic.model.TransferApplication;
import org.springframework.samples.petclinic.repository.TransferAppRepository;
import org.springframework.stereotype.Service;

@Service
public class TransferAppService {

	@Autowired
	private TransferAppRepository transferAppRepository;

	@Autowired
	private BankAccountService bankAccountService;

	@Autowired
	private TransferService transferService;

	@Transactional
	public Collection<TransferApplication> findAllTransfersApplications() {
		return (Collection<TransferApplication>) this.transferAppRepository.findAll();

	}

	@Transactional
	public TransferApplication findTransferAppById(int transferAppId) {
		return this.transferAppRepository.findById(transferAppId).get();
	}

	@Transactional
	public void save(@Valid TransferApplication transferApp) throws DataAccessException {
		this.transferAppRepository.save(transferApp);
	}

	public TransferApplication createTransferApp(String status, Double transferAmount,
			String account_number_destination, BankAccount originAccount) {

		TransferApplication tranfers_application = new TransferApplication();
		tranfers_application.setStatus(status);
		tranfers_application.setAmount(transferAmount);
		tranfers_application.setAccount_number_destination(account_number_destination);
		tranfers_application.setAccount(originAccount);
		return tranfers_application;
	}

	public void acceptApp(TransferApplication transferApplication) {
		transferApplication.setStatus("ACCEPTED");
		Double transferAmount = transferApplication.getAmount();
		this.save(transferApplication);

		String accountOrigin = transferApplication.getAccount().getAccountNumber();
		BankAccount originAccount = this.bankAccountService.findBankAccountByNumber(accountOrigin);

		BankAccount destinationAccount = this.bankAccountService
				.findBankAccountByNumber(transferApplication.getAccount_number_destination());

		this.transferService.setMoney(transferAmount, destinationAccount, originAccount);
	}

	public void refuseApp(TransferApplication transferApplication) {
		transferApplication.setStatus("REJECTED");
		this.save(transferApplication);
	}

}
