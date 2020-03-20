package org.springframework.samples.acmerico.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.acmerico.model.Employee;

public interface EmployeeRepository {

	Collection<Employee> findByLastName(String lastName) throws DataAccessException;

	Object findByUserName(String name) throws DataAccessException;

	Employee findById(int employeeId) throws DataAccessException;

	void save(Employee employee) throws DataAccessException;

	void deleteEmployee(int employeeId) throws DataAccessException;

}
