package org.springframework.samples.petclinic.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.CreditCard;

public interface CreditCardRepository extends CrudRepository<CreditCard, String>{

	CreditCard findCardById(int id) throws DataAccessException;

}
