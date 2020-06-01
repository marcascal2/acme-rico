package org.springframework.samples.acmerico.repository.springdatajpa;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.samples.acmerico.model.LoanApplication;
import org.springframework.samples.acmerico.projections.ClientLoanApp;
import org.springframework.samples.acmerico.repository.LoanApplicationRepository;

public interface SpringDataLoanAppRepository extends LoanApplicationRepository, Repository<LoanApplication, Integer> {

	@Override
	@Query("SELECT loanApp.amount AS amount, loanApp.id AS id, loanApp.purpose AS purpose, loan.description AS loanDescription, "
	+ "loanApp.status AS status, loanApp.amount_paid AS amount_paid, loan.number_of_deadlines AS deadlines, loanApp.payedDeadlines AS payedDeadlines "		
	+  "FROM LoanApplication loanApp INNER JOIN loanApp.loan loan WHERE loanApp.status = 'ACCEPTED'")
	Collection<ClientLoanApp> findLoanAppAccepted() throws DataAccessException;
	
	@Override
	@Query("SELECT loanApp.amount AS amount, loanApp.id AS id, loanApp.purpose AS purpose, loan.description AS loanDescription, "
	+ "loanApp.status AS status, loanApp.amount_paid AS amount_paid, loan.number_of_deadlines AS deadlines, loanApp.payedDeadlines AS payedDeadlines "		
	+  "FROM LoanApplication loanApp INNER JOIN loanApp.loan loan WHERE loanApp.status = 'PENDING'")
	Collection<ClientLoanApp> findPendings() throws DataAccessException;
	
	@Override
	@Query("SELECT loan_application FROM LoanApplication loan_application WHERE loan_application.status = 'ACCEPTED'")
	Collection<LoanApplication> findLoanAppsToCollect();
	
}
