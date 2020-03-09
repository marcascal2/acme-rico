package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.BankAccount;
import org.springframework.samples.petclinic.repository.BankAccountRepository;
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

	@Transactional
	public void saveBankAccount(BankAccount BankAccount) throws DataAccessException {
		//creating BankAccount
		bankAccountRepository.save(BankAccount);		
	}		
}
