package org.springframework.samples.acmerico.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.acmerico.model.BankAccount;
import org.springframework.samples.acmerico.model.Client;

public interface BankAccountRepository extends CrudRepository<BankAccount, Integer> {
	
	BankAccount findById(int id);
	
	Collection<BankAccount> findByClient(Client client)  throws DataAccessException;

	Collection<BankAccount> findByAccountNumber(String accountNumber);

	@Query("SELECT account FROM BankAccount account WHERE account.accountNumber =:account_number_destination")
	BankAccount findByAccounNumber(@Param("account_number_destination") String accountNumberDestination);

}
