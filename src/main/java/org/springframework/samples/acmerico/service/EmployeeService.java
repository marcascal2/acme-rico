package org.springframework.samples.acmerico.service;

import java.util.Collection;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.acmerico.model.Employee;
import org.springframework.samples.acmerico.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeService {

	private EmployeeRepository employeeRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthoritiesService authoritiesService;
	
	@Autowired
	public EmployeeService(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	@Transactional(readOnly = true)
	public Collection<Employee> findEmployeeByLastName(String lastName) throws DataAccessException {
		return employeeRepository.findByLastName(lastName);
	}

	@Transactional(readOnly = true)
	public Object findEmployeeByUserName(String name) throws DataAccessException {
		return employeeRepository.findByUserName(name);
	}

	@Transactional(readOnly = true)
	public Employee findEmployeeById(int employeeId) throws DataAccessException {
		return employeeRepository.findById(employeeId);
	}
	
	@Transactional
	public void saveEmployee(Employee employee) throws DataAccessException {
		//creating employee
		employeeRepository.save(employee);		
		//creating user
		userService.saveUser(employee.getUser());
		//creating authorities
		authoritiesService.saveAuthorities(employee.getUser().getUsername(), "employee");
	}

	@Transactional
	public void deleteEmployeeById(int employeeId) throws DataAccessException{
		employeeRepository.deleteEmployee(employeeId);
	}
	
}
