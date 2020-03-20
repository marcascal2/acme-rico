package org.springframework.samples.acmerico.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.acmerico.model.BankAccount;
import org.springframework.samples.acmerico.model.Client;

public interface BankAccountRepository extends CrudRepository<BankAccount, Integer> {
	
	BankAccount findById(int id) throws DataAccessException;
	
	Collection<BankAccount> findByClient(Client client)  throws DataAccessException;

	Collection<BankAccount> findByAccountNumber(String accountNumber) throws DataAccessException;

}
