package org.springframework.samples.acmerico.repository;

import java.util.Collection;

import org.springframework.samples.acmerico.model.Client;

public interface ClientRepository {
	
	Collection<Client> findByLastName(String lastName);

	Client findById(int id);

	void save(Client client);
	
	Client findByUserName(String name);

}
