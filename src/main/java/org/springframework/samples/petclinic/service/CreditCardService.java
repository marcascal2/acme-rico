package org.springframework.samples.petclinic.service;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
	public void saveCreditCard(@Valid CreditCard creditCard) {
		this.creditCardRepository.save(creditCard);
	}

	@Transactional
	public CreditCard findCreditCardById(int cardId) {
		CreditCard cc = this.creditCardRepository.findCardById(cardId);
		return null;
	}	
}
