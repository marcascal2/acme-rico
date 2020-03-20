package org.springframework.samples.acmerico.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.acmerico.model.Client;
import org.springframework.samples.acmerico.model.User;

public interface ClientRepository {
	
	Collection<Client> findByLastName(String lastName) throws DataAccessException;

	Client findById(int id) throws DataAccessException;

	void save(Client client) throws DataAccessException;
	
	Client findByUserName(String name) throws DataAccessException;

	void delete(User user) throws DataAccessException;

}
