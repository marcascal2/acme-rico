package org.springframework.samples.acmerico.service;

import java.util.Collection;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.acmerico.model.Client;
import org.springframework.samples.acmerico.model.Debt;
import org.springframework.samples.acmerico.repository.DebtRepository;
import org.springframework.stereotype.Service;

@Service
public class DebtService {
	
	@Autowired
	private DebtRepository debtRepository;
	
	@Autowired
	public DebtService(DebtRepository debtRepository) {
		this.debtRepository = debtRepository;
	}

	@Transactional
	public void save(@Valid Debt debt) throws DataAccessException {
		this.debtRepository.save(debt);
	}
	
	@Transactional
	public Collection<Debt> getNonPayedDebts() throws DataAccessException {
		Collection<Debt> allDebts = (Collection<Debt>) this.debtRepository.findAll();
		return allDebts.stream().filter(x -> x.getAmount() != 0).collect(Collectors.toList());
	}
	
	@Transactional
	public Debt getClientDebt(Client client) {
		return this.debtRepository.findByClientId(client.getId());
	}
}
