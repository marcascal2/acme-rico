package org.springframework.samples.acmerico.repository;

import java.util.Collection;

import org.springframework.samples.acmerico.model.Debt;

public interface DebtRepository {

	Debt findByClientId(int clientId);
	
	void save(Debt debt);
	
	Collection<Debt> findAll();
}
