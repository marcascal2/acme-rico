package org.springframework.samples.acmerico.repository;
import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.acmerico.model.CreditCardApplication;

public interface CreditCardAppRepository extends CrudRepository<CreditCardApplication, Integer>{
	
	Collection<CreditCardApplication> findAppByClientId(int id) throws DataAccessException;

}
