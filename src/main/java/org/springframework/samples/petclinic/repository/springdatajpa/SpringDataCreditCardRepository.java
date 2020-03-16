package org.springframework.samples.petclinic.repository.springdatajpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.CreditCard;
import org.springframework.samples.petclinic.repository.CreditCardRepository;

public interface SpringDataCreditCardRepository extends CreditCardRepository, Repository<CreditCard, Integer> {
	
	@Override
	@Query("SELECT credit_card FROM CreditCard credit_card WHERE credit_card.id LIKE :id")
	CreditCard findCardById(int id);

}
