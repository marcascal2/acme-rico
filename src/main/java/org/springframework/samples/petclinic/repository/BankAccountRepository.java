package org.springframework.samples.petclinic.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.BankAccount;

public interface BankAccountRepository extends CrudRepository<BankAccount, String> {
	
	BankAccount findById(int id) throws DataAccessException;
	
}
