package org.springframework.samples.acmerico.service;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
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
	public BankAccount findBankAccountById(int id) {
		return bankAccountRepository.findById(id);
	}

	@Transactional(readOnly = true)
	public Collection<BankAccount> findBankAccountByClient(Client client) {
		return bankAccountRepository.findByClient(client);
	}
	
	@Transactional
	public void saveBankAccount(BankAccount bankAccount) {
		bankAccountRepository.save(bankAccount);		
	}

	@Transactional
	public Collection<BankAccount> findBankAccountByAccountNumber(String accountNumber) {
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
	public BankAccount findBankAccountByNumber(String accountNumberDestination) {
		return bankAccountRepository.findByAccounNumber(accountNumberDestination);
	}

	public void sumAmount(Double transferAmount, BankAccount destinationAccount) {
		Double amount = destinationAccount.getAmount() + transferAmount;
		destinationAccount.setAmount(amount);
	}

	public void substractAmount(Double transferAmount, BankAccount originAccount) {
		Double amount = originAccount.getAmount() - transferAmount;
		originAccount.setAmount(amount);
	}

	// Comprueba si el número está repetido a la hora de generar
	public Boolean accountNumberAlreadyUsed(String accountNumber) {
		BankAccount bankAccount = this.bankAccountRepository.findByAccounNumber(accountNumber);
		return bankAccount != null;
	} 
	
}
