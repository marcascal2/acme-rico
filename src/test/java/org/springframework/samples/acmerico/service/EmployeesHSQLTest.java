package org.springframework.samples.acmerico.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.acmerico.model.Employee;
import org.springframework.samples.acmerico.model.User;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class EmployeesHSQLTest {

	@Autowired
	private EmployeeService service;

	Employee new_employee = new Employee();
	
	@BeforeEach
	private void populateData() {
		User new_user = new User();
		new_user.setUsername("user");
		new_user.setPassword("pass");
		new_user.setEnabled(true);
		new_employee.setFirstName("Name");
		new_employee.setLastName("Surname");
		new_employee.setSalary(33.00);
		new_employee.setUser(new_user);
		this.service.saveEmployee(new_employee);
	}

	@Test
	public void testCountEmployeesAfterCreating() {
		Collection<Employee> employees = this.service.findEmployeeByLastName("");
		assertThat(employees.size()).isEqualTo(4);
	}

	@Test
	public void testCountEmployeesByLastName() {
		Collection<Employee> employees = this.service.findEmployeeByLastName("Surname");
		assertThat(employees.size()).isEqualTo(1);
	}

	@Test
	public void testCountEmployeesByUserName() {
		Employee employees = (Employee) this.service.findEmployeeByUserName("user");
		assertThat(employees.getFirstName()).isEqualTo("Name");
	}

	@Test
	public void saveInvalidEmployee() {
		new_employee.setUser(null);
		assertThrows(NullPointerException.class, () -> this.service.saveEmployee(new_employee));
	}
}
