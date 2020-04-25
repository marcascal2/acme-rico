package org.springframework.samples.acmerico.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.acmerico.model.CreditCard;

public interface CreditCardRepository {

	CreditCard findById(int id) throws DataAccessException;
	
	CreditCard findByNumber(String number) throws DataAccessException;

	void deleteCreditCard(int id) throws DataAccessException;

	void save(CreditCard creditCard) throws DataAccessException;

	Collection<CreditCard> findAll();

}
