package org.springframework.samples.acmerico.repository;

import java.util.Collection;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.acmerico.model.LoanApplication;

public interface LoanApplicationRepository extends CrudRepository<LoanApplication, Integer> {

	Collection<LoanApplication> findAppByClientId(int id) throws DataAccessException;

	@Query("SELECT loan_application FROM LoanApplication loan_application WHERE loan_application.status = 'ACCEPTED'")
	Collection<LoanApplication> findLoanAppAccepted() throws DataAccessException;

}