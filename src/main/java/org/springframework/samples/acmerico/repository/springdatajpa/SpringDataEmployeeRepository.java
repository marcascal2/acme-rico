package org.springframework.samples.acmerico.repository.springdatajpa;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.acmerico.model.Employee;
import org.springframework.samples.acmerico.repository.EmployeeRepository;

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

	@Transactional
	@Modifying
	@Query("DELETE FROM Employee e WHERE e.id =:id")
	public void deleteEmployee(@Param("id") int id);
}
