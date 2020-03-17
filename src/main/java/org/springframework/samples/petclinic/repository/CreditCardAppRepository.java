package org.springframework.samples.petclinic.repository;
import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.CreditCardApplication;

public interface CreditCardAppRepository extends CrudRepository<CreditCardApplication, Integer>{
	
	Collection<CreditCardApplication> findAppByClientId(int id) throws DataAccessException;

}
