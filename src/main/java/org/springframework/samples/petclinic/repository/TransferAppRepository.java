package org.springframework.samples.petclinic.repository;

import java.util.Collection;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.model.TransferApplication;

public interface TransferAppRepository extends CrudRepository<TransferApplication, Integer> {

	Collection<TransferApplication> findAllByClient(Client client) throws DataAccessException;

}
