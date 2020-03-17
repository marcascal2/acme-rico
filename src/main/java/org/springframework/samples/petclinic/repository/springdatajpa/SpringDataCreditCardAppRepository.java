package org.springframework.samples.petclinic.repository.springdatajpa;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.samples.petclinic.model.CreditCardApplication;
import org.springframework.samples.petclinic.repository.CreditCardAppRepository;

public interface SpringDataCreditCardAppRepository extends CreditCardAppRepository {
	
	@Override
	@Query("SELECT credit_card_app FROM CreditCardApplication credit_card_app WHERE credit_card_app.client.id LIKE :id")
	Collection<CreditCardApplication> findAppByClientId(int id);

}
