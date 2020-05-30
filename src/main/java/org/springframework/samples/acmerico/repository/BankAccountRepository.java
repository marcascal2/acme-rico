package org.springframework.samples.acmerico.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.acmerico.model.BankAccount;
import org.springframework.samples.acmerico.model.Client;

public interface BankAccountRepository extends CrudRepository<BankAccount, Integer> {
	
	BankAccount findById(int id) throws DataAccessException;
	
	Collection<BankAccount> findByClient(Client client);

	Collection<BankAccount> findByAccountNumber(String accountNumber) throws DataAccessException;

	@Query("SELECT account FROM BankAccount account WHERE account.accountNumber =:account_number_destination")
	BankAccount findByAccounNumber(@Param("account_number_destination") String account_number_destination)
	throws DataAccessException;

}
