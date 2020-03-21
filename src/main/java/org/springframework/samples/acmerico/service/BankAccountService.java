package org.springframework.samples.acmerico.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.acmerico.model.BankAccount;
import org.springframework.samples.acmerico.model.Client;
import org.springframework.samples.acmerico.repository.BankAccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BankAccountService {
	
	private BankAccountRepository bankAccountRepository;
	
	@Autowired
	public BankAccountService(BankAccountRepository bankAccountRepository) {
		this.bankAccountRepository = bankAccountRepository;
	}	
	
	@Transactional(readOnly = true)
	public BankAccount findBankAccountById(int id) throws DataAccessException {
		return bankAccountRepository.findById(id);
	}

	@Transactional(readOnly = true)
	public Collection<BankAccount> findBankAccountByClient(Client client) throws DataAccessException {
		return bankAccountRepository.findByClient(client);
	}
	
	@Transactional
	public void saveBankAccount(BankAccount BankAccount) throws DataAccessException {
		//creating BankAccount
		bankAccountRepository.save(BankAccount);		
	}

	@Transactional
	public Collection<BankAccount> findBankAccountByAccountNumber(String accountNumber) throws DataAccessException {
		return bankAccountRepository.findByAccountNumber(accountNumber);
	}

	@Transactional
	public Collection<BankAccount> findBankAccounts() {
		return (Collection<BankAccount>) bankAccountRepository.findAll();
	}

	@Transactional
	public void deleteAccount(BankAccount bankAccount) {
		bankAccountRepository.delete(bankAccount);
	}

	@Transactional
	public BankAccount findBankAccountByNumber(String account_number_destination) {
		return bankAccountRepository.findByAccounNumber(account_number_destination);
	}

	public void sumAmount(Double transferAmount, BankAccount destinationAccount) {
		Double amount = destinationAccount.getAmount() + transferAmount;
		destinationAccount.setAmount(amount);
	}

	public void SubstractAmount(Double transferAmount, BankAccount originAccount) {
		Double amount = originAccount.getAmount() - transferAmount;
		originAccount.setAmount(amount);
	}
	
}
