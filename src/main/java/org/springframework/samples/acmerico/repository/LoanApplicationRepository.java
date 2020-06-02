package org.springframework.samples.acmerico.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.acmerico.model.LoanApplication;
import org.springframework.samples.acmerico.projections.ClientLoanApp;

public interface LoanApplicationRepository {
	
	void save(LoanApplication loanApp);
	
	Optional<LoanApplication> findById(int id);

	Collection<LoanApplication> findAppByClientId(int id) throws DataAccessException;

	Collection<ClientLoanApp> findLoanAppAccepted() throws DataAccessException;
	
	Collection<ClientLoanApp> findPendings() throws DataAccessException;
	
	Collection <LoanApplication> findLoanAppsToCollect();

}