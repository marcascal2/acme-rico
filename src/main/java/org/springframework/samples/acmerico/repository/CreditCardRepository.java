package org.springframework.samples.acmerico.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.acmerico.model.CreditCard;

public interface CreditCardRepository extends CrudRepository<CreditCard, Integer> {

	CreditCard findById(int id) throws DataAccessException;
	
	@Query("SELECT creditCard FROM CreditCard creditCard WHERE creditCard.number =:number")
	CreditCard findByNumber(@Param("number") String number) throws DataAccessException;

	@Modifying
	@Query("DELETE FROM CreditCard creditCard WHERE creditCard.id =:id")
	void deleteCard(@Param(value = "id") int id) throws DataAccessException;

	
}
