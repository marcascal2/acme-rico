package org.springframework.samples.acmerico.repository;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.acmerico.model.CreditCard;

public interface CreditCardRepository extends CrudRepository<CreditCard, Integer> {

	CreditCard findById(int id) throws DataAccessException;
	
	CreditCard findByNumber(String number);

	Collection<CreditCard> findAll();
	
    @Transactional
	@Modifying
	@Query("DELETE FROM CreditCard cc WHERE cc.id =:id")
	public void deleteCreditCard(@Param("id") int id);

}
