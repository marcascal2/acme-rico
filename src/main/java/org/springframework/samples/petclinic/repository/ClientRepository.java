package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Client;

public interface ClientRepository {
	
	Collection<Client> findByLastName(String lastName) throws DataAccessException;

	Client findById(int id) throws DataAccessException;

	void save(Client client) throws DataAccessException;
	
	Client findClientByUsername(String username) throws DataAccessException;

}
