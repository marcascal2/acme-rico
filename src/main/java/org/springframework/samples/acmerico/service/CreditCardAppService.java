package org.springframework.samples.acmerico.service;

import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.acmerico.model.CreditCardApplication;
import org.springframework.samples.acmerico.repository.CreditCardAppRepository;
import org.springframework.stereotype.Service;

@Service
public class CreditCardAppService {
	
	private CreditCardAppRepository creditCardAppRepository;
	
	@Autowired
	public CreditCardAppService(CreditCardAppRepository creditCardAppRepository) {
		this.creditCardAppRepository = creditCardAppRepository;
	}

	@Transactional
	public Collection<CreditCardApplication> findCreditCardApps() {
		return (Collection<CreditCardApplication>) this.creditCardAppRepository.findAll();
	}
	
	@Transactional
	public CreditCardApplication findCreditCardAppById(int creditCardAppId) {
		return this.creditCardAppRepository.findById(creditCardAppId).get();
	}
	
	@Transactional()
	public Collection<CreditCardApplication> findCreditCardAppByClientId(int id) throws DataAccessException {
		Collection<CreditCardApplication> cc_app = this.creditCardAppRepository.findAppByClientId(id);
		return cc_app;
	}	
  
  	@Transactional
	public void save(@Valid CreditCardApplication creditCardApp) throws DataAccessException {
		this.creditCardAppRepository.save(creditCardApp);
	}
  	
  	public void acceptApp(CreditCardApplication creditCardApp) {
  		creditCardApp.setStatus("ACCEPTED");
		this.save(creditCardApp);
	}

	public void refuseApp(CreditCardApplication creditCardApp) {
		creditCardApp.setStatus("REJECTED");
		this.save(creditCardApp);
	}

}
