package org.springframework.samples.acmerico.repository;

import java.util.Collection;

import org.springframework.samples.acmerico.model.Employee;

public interface EmployeeRepository {

	Collection<Employee> findByLastName(String lastName);

	Object findByUserName(String name);

	Employee findById(int employeeId);

	void save(Employee employee);

	void deleteEmployee(int employeeId);

}
