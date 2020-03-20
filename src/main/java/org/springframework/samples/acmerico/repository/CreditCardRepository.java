package org.springframework.samples.acmerico.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.acmerico.model.CreditCard;

public interface CreditCardRepository {

	CreditCard findById(int id) throws DataAccessException;

}
