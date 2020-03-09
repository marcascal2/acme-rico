package org.springframework.samples.petclinic.repository.springdatajpa;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Client;
import org.springframework.samples.petclinic.repository.ClientRepository;

public interface SpringDataClientRepository extends ClientRepository, Repository<Client, Integer> {

	@Override
	@Query("SELECT DISTINCT client FROM Client client WHERE client.lastName LIKE :lastName%")
	public Collection<Client> findByLastName(@Param("lastName") String lastName);

	@Override
	@Query("SELECT client FROM Client client WHERE client.id =:id")
	public Client findById(@Param("id") int id);
	
	@Override
	@Query("SELECT client FROM Client client WHERE client.user.username = :username")
	public Client findClientByUsername(@Param( "username") String username) throws DataAccessException;
	
}
