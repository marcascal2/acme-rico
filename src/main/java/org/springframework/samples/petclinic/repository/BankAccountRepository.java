package org.springframework.samples.petclinic.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.BankAccount;

public interface BankAccountRepository extends CrudRepository<BankAccount, String> {
	
	BankAccount findById(int id) throws DataAccessException;

	
	@Query("SELECT account FROM BankAccount account WHERE account.accountNumber =:account_number_destination")
	BankAccount findByAccountNumber(@Param("account_number_destination") String account_number_destination)
			throws DataAccessException;
	
	
	
}
