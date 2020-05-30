package org.springframework.samples.acmerico.service;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
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
		return this.creditCardRepository.findAll();
	}

	@Transactional
	public void saveCreditCard(CreditCard cc) {
		this.creditCardRepository.save(cc);
	}

	@Transactional(readOnly = true)
	public CreditCard findCreditCardById(int id) {
		return this.creditCardRepository.findById(id);
	}

	@Transactional
  	@CacheEvict(cacheNames = "myCreditCards", allEntries = true)
	public void deleteCreditCardById(int id) {
		this.creditCardRepository.deleteCreditCard(id);
	}
	
}
