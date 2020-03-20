package org.springframework.samples.petclinic.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.Test;
import org.springframework.samples.acmerico.model.Client;
import org.springframework.samples.acmerico.model.Person;
import org.springframework.samples.acmerico.model.User;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class ValidatorClientsTest {
	
	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}
	
	@Test
	public void testCreationClientsWhitoutAddress() {
		Client new_client = new Client();
		User new_user = new User();
		LocalDate bday = LocalDate.of(1999, 9, 6);
		LocalDate lastEmployDate = LocalDate.of(2019, 8, 23);
		
		new_user.setUsername("clientUser");
		new_user.setPassword("clientPass");
		new_user.setEnabled(true);
		new_client.setFirstName("Maria");
		new_client.setLastName("Casasola");
		new_client.setAddress("");
		new_client.setAge(20);
		new_client.setBirthDate(bday);
		new_client.setCity("Sevilla");
		new_client.setJob("Empresaria");
		new_client.setLastEmployDate(lastEmployDate);
		new_client.setMaritalStatus("Casada");
		new_client.setSalaryPerYear(20000.00);
		new_client.setUser(new_user);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Person>> constraintViolations = validator.validate(new_client);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Person> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("address");
		assertThat(violation.getMessage()).isEqualTo("no puede estar vacío");
	}

	@Test
	public void testCreationClientsWhitoutAge() {
		Client new_client = new Client();
		User new_user = new User();
		LocalDate bday = LocalDate.of(1999, 9, 6);
		LocalDate lastEmployDate = LocalDate.of(2019, 8, 23);
		
		new_user.setUsername("clientUser");
		new_user.setPassword("clientPass");
		new_user.setEnabled(true);
		new_client.setFirstName("Maria");
		new_client.setLastName("Casasola");
		new_client.setAddress("C/ TEBA");
		new_client.setAge(null);
		new_client.setBirthDate(bday);
		new_client.setCity("Sevilla");
		new_client.setJob("Empresaria");
		new_client.setLastEmployDate(lastEmployDate);
		new_client.setMaritalStatus("Casada");
		new_client.setSalaryPerYear(20000.00);
		new_client.setUser(new_user);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Person>> constraintViolations = validator.validate(new_client);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Person> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("age");
		assertThat(violation.getMessage()).isEqualTo("no puede ser null");
	}
	
	@Test
	public void testCreationClientsWhitoutBirthday() {
		Client new_client = new Client();
		User new_user = new User();
		LocalDate lastEmployDate = LocalDate.of(2019, 8, 23);
		
		new_user.setUsername("clientUser");
		new_user.setPassword("clientPass");
		new_user.setEnabled(true);
		new_client.setFirstName("Maria");
		new_client.setLastName("Casasola");
		new_client.setAddress("C/ TEBA");
		new_client.setAge(20);
		new_client.setBirthDate(null);
		new_client.setCity("Sevilla");
		new_client.setJob("Empresaria");
		new_client.setLastEmployDate(lastEmployDate);
		new_client.setMaritalStatus("Casada");
		new_client.setSalaryPerYear(20000.00);
		new_client.setUser(new_user);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Person>> constraintViolations = validator.validate(new_client);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Person> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("birthDate");
		assertThat(violation.getMessage()).isEqualTo("no puede ser null");
	}
	
	@Test
	public void testCreationClientsWhitoutCity() {
		Client new_client = new Client();
		User new_user = new User();
		LocalDate bday = LocalDate.of(1999, 9, 6);
		LocalDate lastEmployDate = LocalDate.of(2019, 8, 23);
		
		new_user.setUsername("clientUser");
		new_user.setPassword("clientPass");
		new_user.setEnabled(true);
		new_client.setFirstName("Maria");
		new_client.setLastName("Casasola");
		new_client.setAddress("C/ TEBA");
		new_client.setAge(20);
		new_client.setBirthDate(bday);
		new_client.setCity("");
		new_client.setJob("Empresaria");
		new_client.setLastEmployDate(lastEmployDate);
		new_client.setMaritalStatus("Casada");
		new_client.setSalaryPerYear(20000.00);
		new_client.setUser(new_user);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Person>> constraintViolations = validator.validate(new_client);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Person> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("city");
		assertThat(violation.getMessage()).isEqualTo("no puede estar vacío");
	}
	
	@Test
	public void testCreationClientsWhitoutJob() {
		Client new_client = new Client();
		User new_user = new User();
		LocalDate bday = LocalDate.of(1999, 9, 6);
		LocalDate lastEmployDate = LocalDate.of(2019, 8, 23);
		
		new_user.setUsername("clientUser");
		new_user.setPassword("clientPass");
		new_user.setEnabled(true);
		new_client.setFirstName("Maria");
		new_client.setLastName("Casasola");
		new_client.setAddress("C/ TEBA");
		new_client.setAge(20);
		new_client.setBirthDate(bday);
		new_client.setCity("Sevilla");
		new_client.setJob("");
		new_client.setLastEmployDate(lastEmployDate);
		new_client.setMaritalStatus("Casada");
		new_client.setSalaryPerYear(20000.00);
		new_client.setUser(new_user);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Person>> constraintViolations = validator.validate(new_client);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Person> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("job");
		assertThat(violation.getMessage()).isEqualTo("no puede estar vacío");
	}
	
	@Test
	public void testCreationClientsWhitoutMaritalStatus() {
		Client new_client = new Client();
		User new_user = new User();
		LocalDate bday = LocalDate.of(1999, 9, 6);
		LocalDate lastEmployDate = LocalDate.of(2019, 8, 23);
		
		new_user.setUsername("clientUser");
		new_user.setPassword("clientPass");
		new_user.setEnabled(true);
		new_client.setFirstName("Maria");
		new_client.setLastName("Casasola");
		new_client.setAddress("C/ TEBA");
		new_client.setAge(20);
		new_client.setBirthDate(bday);
		new_client.setCity("Sevilla");
		new_client.setJob("Empresaria");
		new_client.setLastEmployDate(lastEmployDate);
		new_client.setMaritalStatus("");
		new_client.setSalaryPerYear(20000.00);
		new_client.setUser(new_user);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Person>> constraintViolations = validator.validate(new_client);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Person> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("maritalStatus");
		assertThat(violation.getMessage()).isEqualTo("no puede estar vacío");
	}
	
	@Test
	public void testCreationClientsWhitoutSalaryPerYear() {
		Client new_client = new Client();
		User new_user = new User();
		LocalDate bday = LocalDate.of(1999, 9, 6);
		LocalDate lastEmployDate = LocalDate.of(2019, 8, 23);
		
		new_user.setUsername("clientUser");
		new_user.setPassword("clientPass");
		new_user.setEnabled(true);
		new_client.setFirstName("Maria");
		new_client.setLastName("Casasola");
		new_client.setAddress("C/ TEBA");
		new_client.setAge(20);
		new_client.setBirthDate(bday);
		new_client.setCity("Sevilla");
		new_client.setJob("Empresaria");
		new_client.setLastEmployDate(lastEmployDate);
		new_client.setMaritalStatus("Casada");
		new_client.setSalaryPerYear(null);
		new_client.setUser(new_user);
		
		Validator validator = createValidator();
		Set<ConstraintViolation<Person>> constraintViolations = validator.validate(new_client);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Person> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("salaryPerYear");
		assertThat(violation.getMessage()).isEqualTo("no puede ser null");
	}
}
