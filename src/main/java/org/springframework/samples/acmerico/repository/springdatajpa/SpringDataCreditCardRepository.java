package org.springframework.samples.acmerico.repository.springdatajpa;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.acmerico.model.CreditCard;
import org.springframework.samples.acmerico.repository.CreditCardRepository;

public interface SpringDataCreditCardRepository extends CreditCardRepository, Repository<CreditCard, Integer> {

    @Transactional
	@Modifying
	@Query("DELETE FROM CreditCard cc WHERE cc.id =:id")
	public void deleteCreditCard(@Param("id") int id);

	@Query("SELECT creditCard FROM CreditCard creditCard WHERE creditCard.number =:number")
	public CreditCard findByNumber(@Param("number") String number);
}
