package org.springframework.samples.acmerico.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.acmerico.model.Employee;
import org.springframework.samples.acmerico.model.User;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class EmployeesTest {
		
		@Autowired
		private EmployeeService service;
		
		@Test
		public void testCountEmployees() {
			Collection<Employee> employees = this.service.findEmployeeByLastName("");
			assertThat(employees.size()).isEqualTo(3);
		}
		
		@Test
		public void testCountEmployeesAfterCreating() {
			Employee new_employee = new Employee();
			User new_user = new User();
			new_user.setUsername("user");
			new_user.setPassword("pass");
			new_user.setEnabled(true);
			new_employee.setFirstName("Moises");
			new_employee.setLastName("Calzado");
			new_employee.setSalary(33.00);
			new_employee.setUser(new_user);
			this.service.saveEmployee(new_employee);
			Collection<Employee> employees = this.service.findEmployeeByLastName("");
			assertThat(employees.size()).isEqualTo(4);
		}
		
		@Test
		public void testCountEmployeesByLastName() {
			Employee new_employee = new Employee();
			User new_user = new User();
			new_user.setUsername("user");
			new_user.setPassword("pass");
			new_user.setEnabled(true);
			new_employee.setFirstName("Moises");
			new_employee.setLastName("Calzado");
			new_employee.setSalary(33.00);
			new_employee.setUser(new_user);
			this.service.saveEmployee(new_employee);
			Collection<Employee> employees = this.service.findEmployeeByLastName("Calzado");
			assertThat(employees.size()).isEqualTo(1);
		}
		
		@Test
		public void testCountEmployeesByUserName() {
			Employee new_employee = new Employee();
			User new_user = new User();
			new_user.setUsername("user");
			new_user.setPassword("pass");
			new_user.setEnabled(true);
			new_employee.setFirstName("Moises");
			new_employee.setLastName("Calzado");
			new_employee.setSalary(33.00);
			new_employee.setUser(new_user);
			this.service.saveEmployee(new_employee);
			Employee employees = (Employee) this.service.findEmployeeByUserName("user");
			assertThat(employees.getFirstName()).isEqualTo("Moises");
		}
}
