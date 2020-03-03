package org.springframework.samples.petclinic.repository.springdatajpa;

import java.util.Collection;

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
	@Query("SELECT client FROM Client client WHERE owner.id =:id")
	public Client findById(@Param("id") int id);
	
}