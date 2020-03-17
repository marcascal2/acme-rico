package org.springframework.samples.petclinic.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.CreditCardApplication;

public interface CreditCardAppRepository extends CrudRepository<CreditCardApplication, Integer>{
	
}
