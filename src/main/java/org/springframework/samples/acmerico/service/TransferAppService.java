package org.springframework.samples.acmerico.service;

import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.acmerico.model.BankAccount;
import org.springframework.samples.acmerico.model.Client;
import org.springframework.samples.acmerico.model.InstantTransfer;
import org.springframework.samples.acmerico.model.TransferApplication;
import org.springframework.samples.acmerico.repository.TransferAppRepository;
import org.springframework.stereotype.Service;

@Service
public class TransferAppService {

	@Autowired
	private TransferAppRepository transferAppRepository;

	@Autowired
	private InstantTransferService instantService;
	
	@Autowired
	private BankAccountService	bankAccountService;

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

	public void setMoney(@Valid TransferApplication transfer_app) {
		Double transferAmount = transfer_app.getAmount();
		String destination = transfer_app.getAccount_number_destination();
		BankAccount destinationAccount = this.bankAccountService.findBankAccountByNumber(destination);
		BankAccount originAccount = transfer_app.getBankAccount();
		
		if (destination != null) {
			// Descontamos el dinero a la cuenta origen y se lo añadimos al destino

			this.bankAccountService.sumAmount(transferAmount, destinationAccount);

			this.bankAccountService.SubstractAmount(transferAmount, originAccount);

			this.bankAccountService.saveBankAccount(originAccount);
			this.bankAccountService.saveBankAccount(destinationAccount);

		} else {
			// Solo le quitamos el dinero a la cuenta origen
			this.bankAccountService.SubstractAmount(transferAmount, originAccount);
			this.bankAccountService.saveBankAccount(originAccount);

		}
	}

	public void checkInstant(@Valid TransferApplication transfer_app) {
		Double amount = transfer_app.getAmount();
		String account_number_destination = transfer_app.getAccount_number_destination();
		BankAccount account_origin = transfer_app.getBankAccount();

		Double minAmount = 100.0;

		if (amount < minAmount) {
			InstantTransfer inst = new InstantTransfer();
			inst.setAmount(amount);
			inst.setDestination(account_number_destination);
			inst.setClient(transfer_app.getClient());
			inst.setBankAccount(account_origin);
			instantService.save(inst);
			
			this.setMoney(transfer_app);


		} else {

			this.save(transfer_app);

		}

	}
	

	public void acceptApp(TransferApplication transferApplication) {
		transferApplication.setStatus("ACCEPTED");
		this.save(transferApplication);

		this.setMoney(transferApplication);
	}

	public void refuseApp(TransferApplication transferApplication) {
		transferApplication.setStatus("REJECTED");
		this.save(transferApplication);
	}

	@Transactional
	public Collection<TransferApplication> findAllTransfersApplicationsByClient(Client client) {
		return this.transferAppRepository.findAllByClient(client);
	}

}
