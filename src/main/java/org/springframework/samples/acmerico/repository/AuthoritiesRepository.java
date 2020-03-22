package org.springframework.samples.acmerico.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.acmerico.model.Authorities;



public interface AuthoritiesRepository extends  CrudRepository<Authorities, String>{
	
}
