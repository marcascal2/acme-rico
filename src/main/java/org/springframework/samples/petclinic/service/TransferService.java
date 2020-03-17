package org.springframework.samples.petclinic.service;

import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.BankAccount;
import org.springframework.samples.petclinic.model.Transfer;
import org.springframework.samples.petclinic.repository.TransferRepository;
import org.springframework.stereotype.Service;

@Service
public class TransferService {

	@Autowired
	private TransferRepository transferRepository;

	@Autowired
	private BankAccountService bankAccountService;

	@Transactional
	public Collection<Transfer> findAllTransfers() {
		return (Collection<Transfer>) this.transferRepository.findAll();

	}

	@Transactional
	public int count() {
		return (int) this.transferRepository.count();
	}

	@Transactional
	public void save(@Valid Transfer transfer) throws DataAccessException {
		this.transferRepository.save(transfer);
	}

	public void setMoney(Double transferAmount, BankAccount destinationAccount, BankAccount originAccount) {
		if (destinationAccount != null) {
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

}
