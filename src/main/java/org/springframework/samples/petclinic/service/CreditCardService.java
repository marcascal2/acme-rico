package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.CreditCard;
import org.springframework.samples.petclinic.repository.CreditCardRepository;
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
	public void saveCreditCard(CreditCard creditCard) throws DataAccessException {
		this.creditCardRepository.save(creditCard);
	}

	@Transactional(readOnly = true)
	public CreditCard findCreditCardById(int id) throws DataAccessException {
		CreditCard cc = this.creditCardRepository.findCardById(id);
		return cc;
	}	
}
