package org.springframework.samples.acmerico.service;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.acmerico.model.CreditCard;
import org.springframework.samples.acmerico.repository.CreditCardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreditCardService {

	private CreditCardRepository creditCardRepository;
	
	@Autowired
	public CreditCardService(CreditCardRepository creditCardRepository) {
		this.creditCardRepository = creditCardRepository;
	}
	
	@Transactional
	public Collection<CreditCard> findCreditCards() {
		return (Collection<CreditCard>) this.creditCardRepository.findAll();
	}

	@Transactional
	public void saveCreditCard(CreditCard cc) {
		this.creditCardRepository.save(cc);
	}

	@Transactional(readOnly = true)
	public CreditCard findCreditCardById(int id) throws DataAccessException {
		CreditCard cc = this.creditCardRepository.findById(id);
		return cc;
	}

	@Transactional
	public void deleteCreditCardById(int id) throws DataAccessException {
		this.creditCardRepository.deleteCreditCard(id);
	}
	
}
