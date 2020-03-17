package org.springframework.samples.petclinic.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.CreditCard;

public interface CreditCardRepository {

	CreditCard findCardById(int id) throws DataAccessException;

}
