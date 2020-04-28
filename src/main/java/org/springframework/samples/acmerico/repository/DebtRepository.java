package org.springframework.samples.acmerico.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.acmerico.model.Debt;

public interface DebtRepository {

	Debt findByClientId(int clientId) throws DataAccessException;
	
	void save(Debt debt) throws DataAccessException;
	
	Collection<Debt> findAll() throws DataAccessException;
}
