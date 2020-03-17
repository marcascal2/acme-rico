package org.springframework.samples.petclinic.service;

import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.CreditCardApplication;
import org.springframework.samples.petclinic.repository.CreditCardAppRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreditCardAppService {
	
private CreditCardAppRepository creditCardAppRepository;
	
	@Autowired
	public CreditCardAppService(CreditCardAppRepository creditCardAppRepository) {
		this.creditCardAppRepository = creditCardAppRepository;
	}

	@Transactional(readOnly = true)
	public Collection<CreditCardApplication> findCreditCardAppByClientId(int id) throws DataAccessException {
		Collection<CreditCardApplication> cc_app = this.creditCardAppRepository.findAppByClientId(id);
		return cc_app;
	}	
  
  	@Transactional
	public void save(@Valid CreditCardApplication creditCardApp) throws DataAccessException {
		this.creditCardAppRepository.save(creditCardApp);
	}

}
