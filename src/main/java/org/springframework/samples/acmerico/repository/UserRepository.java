package org.springframework.samples.acmerico.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.acmerico.model.User;


public interface UserRepository extends  CrudRepository<User, String>{
	
}
