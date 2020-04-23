package org.springframework.samples.acmerico.service;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
}
