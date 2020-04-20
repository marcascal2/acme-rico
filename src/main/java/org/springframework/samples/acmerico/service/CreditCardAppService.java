package org.springframework.samples.acmerico.service;

import java.time.LocalDate;
import java.util.Collection;
import javax.transaction.Transactional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.acmerico.model.CreditCard;
import org.springframework.samples.acmerico.model.CreditCardApplication;
import org.springframework.samples.acmerico.repository.CreditCardAppRepository;
import org.springframework.samples.acmerico.repository.CreditCardRepository;
import org.springframework.samples.acmerico.util.CreditCardNumberGenerator;
import org.springframework.stereotype.Service;

@Service
public class CreditCardAppService {
	
	private CreditCardAppRepository creditCardAppRepository;
	
	private CreditCardRepository creditCardRepository;
	
	@Autowired
	public CreditCardAppService(CreditCardAppRepository creditCardAppRepository, CreditCardRepository creditCardRepository) {
		this.creditCardAppRepository = creditCardAppRepository;
		this.creditCardRepository = creditCardRepository;
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
  	
  	@Transactional
	public void saveCreditCard(@Valid CreditCard creditCard) throws DataAccessException {
		this.creditCardRepository.save(creditCard);
	}
  	
  	@Transactional
  	public void acceptApp(CreditCardApplication creditCardApp) {
  		LocalDate now = LocalDate.now();
  		int month = now.getMonthValue();
  		String deadLine;
  		
  		if(month<10){
  			deadLine = "0" + now.getMonthValue() + "/" + now.getYear();
  		} else {
  			deadLine = now.getMonthValue() + "/" + now.getYear();
  		}
  		
  		Integer intCvv = (int) (Math.random() * (999 - 0)) + 0;
  		String cvv = intCvv.toString();
  		
  		if(cvv.length() == 1) {
  			cvv = "00" + cvv;
  		} else if (cvv.length() == 2) {
  			cvv = "0" + cvv;
  		}
  		
  		CreditCardNumberGenerator generator = new CreditCardNumberGenerator();
  		String number = generator.generate("4", 16);
  		
  		System.out.println(this.creditCardRepository.findByNumber(number));
  		
  		while(this.creditCardRepository.findByNumber(number) != null) {
  			number = generator.generate("4", 16);
  		}
  		
  		CreditCard creditCard = new CreditCard();
  		creditCard.setNumber(number);
  		creditCard.setDeadline(deadLine);
  		creditCard.setCvv(cvv.toString());
  		creditCard.setBankAccount(creditCardApp.getBankAccount());
  		creditCard.setClient(creditCardApp.getClient());
  		
  		this.saveCreditCard(creditCard);

  		creditCardApp.setStatus("ACCEPTED");
		this.save(creditCardApp);
	}

  	@Transactional
	public void refuseApp(CreditCardApplication creditCardApp) {
		creditCardApp.setStatus("REJECTED");
		this.save(creditCardApp);
	}

}
