package org.springframework.samples.acmerico.repository.springdatajpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.acmerico.model.Debt;
import org.springframework.samples.acmerico.repository.DebtRepository;

public interface SpringDebtRepository extends DebtRepository, Repository<Debt, Integer> {

	@Override
	@Query("SELECT debt FROM Debt debt WHERE debt.client.id =:id")
	public Debt findByClientId(@Param("id") int id);
	
}
