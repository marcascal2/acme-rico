package org.springframework.samples.petclinic.repository.springdatajpa;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Employee;
import org.springframework.samples.petclinic.repository.EmployeeRepository;

public interface SpringDataEmployeeRepository extends EmployeeRepository, Repository<Employee, Integer>{
	
	@Override
	@Query("SELECT DISTINCT employee FROM Employee employee WHERE employee.lastName LIKE :lastName%")
	public Collection<Employee> findByLastName(@Param("lastName") String lastName);

	@Override
	@Query("SELECT employee FROM Employee employee WHERE employee.id =:id")
	public Employee findById(@Param("id") int id);
	
	@Override
	@Query("SELECT employee FROM Employee employee WHERE employee.user.username =:name")
	public Employee findByUserName(@Param("name") String name);
}
