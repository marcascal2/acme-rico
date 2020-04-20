package org.springframework.samples.acmerico.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.acmerico.model.CreditCard;

public interface CreditCardRepository {

	CreditCard findById(int id) throws DataAccessException;
	
	CreditCard findByNumber(@Param("number") String number) throws DataAccessException;

	void save(CreditCard cc) throws DataAccessException;

}
