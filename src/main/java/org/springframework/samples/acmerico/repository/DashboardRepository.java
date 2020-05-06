package org.springframework.samples.acmerico.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.acmerico.model.Client;
import org.springframework.samples.acmerico.model.User;

public interface DashboardRepository extends CrudRepository<User, Integer> {

	@Query("SELECT COUNT(loan_application) FROM LoanApplication loan_application WHERE loan_application.status = 'ACCEPTED' AND loan_application.client LIKE :client")
	Integer countLoanAppsAccepted(@Param("client") Client client) throws DataAccessException;
	
	@Query("SELECT COUNT(loan_application) FROM LoanApplication loan_application WHERE loan_application.status = 'PENDING' AND loan_application.client LIKE :client")
	Integer countLoanAppsPending(@Param("client") Client client) throws DataAccessException;
	
	@Query("SELECT COUNT(loan_application) FROM LoanApplication loan_application WHERE loan_application.status = 'REJECTED' AND loan_application.client LIKE :client")
	Integer countLoanAppsRejected(@Param("client") Client client) throws DataAccessException;
	
	@Query("SELECT COUNT(credit_card_application) FROM CreditCardApplication credit_card_application WHERE credit_card_application.status = 'ACCEPTED' AND credit_card_application.client LIKE :client")
	Integer countCreditCardAppsAccepted(@Param("client") Client client) throws DataAccessException;
	
	@Query("SELECT COUNT(credit_card_application) FROM CreditCardApplication credit_card_application WHERE credit_card_application.status = 'PENDING' AND credit_card_application.client LIKE :client")
	Integer countCreditCardAppsPending(@Param("client") Client client) throws DataAccessException;
	
	@Query("SELECT COUNT(credit_card_application) FROM CreditCardApplication credit_card_application WHERE credit_card_application.status = 'REJECTED' AND credit_card_application.client LIKE :client")
	Integer countCreditCardAppsRejected(@Param("client") Client client) throws DataAccessException;
	
	@Query("SELECT COUNT(transfer_application) FROM TransferApplication transfer_application WHERE transfer_application.status = 'ACCEPTED' AND transfer_application.client LIKE :client")
	Integer countTransferAppsAccepted(@Param("client") Client client) throws DataAccessException;
	
	@Query("SELECT COUNT(transfer_application) FROM TransferApplication transfer_application WHERE transfer_application.status = 'PENDING' AND transfer_application.client LIKE :client")
	Integer countTransferAppsPending(@Param("client") Client client) throws DataAccessException;
	
	@Query("SELECT COUNT(transfer_application) FROM TransferApplication transfer_application WHERE transfer_application.status = 'REJECTED' AND transfer_application.client LIKE :client")
	Integer countTransferAppsRejected(@Param("client") Client client) throws DataAccessException;
	
	@Query("SELECT SUM(amount) FROM Debt debt WHERE debt.client LIKE :client")
	Integer countMoneyToDebt(@Param("client") Client client) throws DataAccessException;
	
	@Query("SELECT SUM(amount) FROM BankAccount bank_account WHERE bank_account.client LIKE :client")
	Integer countMoneyInBankAccounts(@Param("client") Client client) throws DataAccessException;
	
}