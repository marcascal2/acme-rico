package org.springframework.samples.petclinic.repository;


import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.TransferApplication;

public interface TransferAppRepository extends CrudRepository<TransferApplication, Integer> {

	@Query("select a from TransferApplication a where a.client.id =: clientId")
	Collection<TransferApplication> findAllByClientId(@Param("clientId") int clientId) throws DataAccessException;

	

}
