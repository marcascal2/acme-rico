package org.springframework.samples.petclinic.service;

import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.CreditCardApplication;
import org.springframework.samples.petclinic.repository.CreditCardAppRepository;
import org.springframework.stereotype.Service;

@Service
public class CreditCardAppService {

	@Autowired
	private CreditCardAppRepository creditCardAppRepository;

	@Transactional
	public Collection<CreditCardApplication> findAllCreditCardsApplications() {
		return (Collection<CreditCardApplication>) this.creditCardAppRepository.findAll();
	}
	
	@Transactional
	public CreditCardApplication findTransferAppById(int creditCardAppId) {
		return this.creditCardAppRepository.findById(creditCardAppId).get();
	}
	
	@Transactional
	public void save(@Valid CreditCardApplication creditCardApp) throws DataAccessException {
		this.creditCardAppRepository.save(creditCardApp);
	}

}
