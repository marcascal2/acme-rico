package org.springframework.samples.acmerico.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.acmerico.model.Client;
import org.springframework.samples.acmerico.model.TransferApplication;

public interface TransferAppRepository extends CrudRepository<TransferApplication, Integer> {

    Collection<TransferApplication> findAllByClient(Client client) throws DataAccessException;	

}
